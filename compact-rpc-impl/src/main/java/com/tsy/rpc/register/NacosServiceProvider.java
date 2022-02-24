package com.tsy.rpc.register;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.tsy.rpc.base.exception.LoadServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * https://zhuanlan.zhihu.com/p/364711472
 *
 * @author Steven.T
 * @date 2022/2/20
 */
@Slf4j
public class NacosServiceProvider {

    /**
     * TODO:调整为可配置
     */
    private static final String SERVER_ADDRESS = "127.0.0.1:8848";
    private static final NamingService namingService;
    private static final Map<String, List<InetSocketAddress>> SERVICE_INSTANCES_MAP = new ConcurrentHashMap<>();

    static {
        namingService = loadNamingService();
    }

    private static NamingService loadNamingService() {
        try {
            return NamingFactory.createNamingService(SERVER_ADDRESS);
        } catch (NacosException e) {
            throw new LoadServiceException("Error occurs when loading server.");
        }
    }

    public void registerInstance(String serviceName, String hostName,int port) throws NacosException {
        namingService.registerInstance(serviceName, hostName, port);
        InetSocketAddress address =new InetSocketAddress(hostName,port);
        List<InetSocketAddress> inetSocketAddresses;
        if (SERVICE_INSTANCES_MAP.containsKey(serviceName)) {
            inetSocketAddresses = SERVICE_INSTANCES_MAP.get(serviceName);
            inetSocketAddresses.add(address);
        } else {
            inetSocketAddresses = new ArrayList<>();
            inetSocketAddresses.add(address);
            SERVICE_INSTANCES_MAP.put(serviceName, inetSocketAddresses);
        }
    }

    public List<Instance> getAllInstance(String serviceName) throws NacosException {
        return namingService.getAllInstances(serviceName);
    }

    public void deregisterInstance(String serviceName,String hostName,int port) throws NacosException {
        namingService.deregisterInstance(serviceName,hostName,port);
    }

    public void clearAllRegistries() throws NacosException {
        if (MapUtils.isEmpty(SERVICE_INSTANCES_MAP)) {
            for (Map.Entry<String, List<InetSocketAddress>> entry : SERVICE_INSTANCES_MAP.entrySet()) {
                for (InetSocketAddress address : entry.getValue()) {
                    namingService.deregisterInstance(entry.getKey(),address.getHostName(), address.getPort());
                }
            }
        }
    }
}
