package com.tsy.rpc.register;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.tsy.rpc.base.factory.SingletonFactory;
import com.tsy.rpc.base.register.ServiceRegistry;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.Objects;

/**
 * @author Steven.T
 * @date 2022/2/20
 */
@Slf4j
public class NacosServiceRegistry implements ServiceRegistry {

    private final NacosServiceProvider serviceProvider = SingletonFactory.getInstance(NacosServiceProvider.class);

    @Override
    public void registerService(String serviceName, InetSocketAddress inetSocketAddress) {
        checkServiceInfo(serviceName, inetSocketAddress);
        try {
            serviceProvider.registerInstance(serviceName, inetSocketAddress.getHostName(), inetSocketAddress.getPort());
        } catch (NacosException e) {
            log.error("Error occurred when registering service {}.\nError:code:{},msg:{}.",
                    serviceName, e.getErrCode(), e.getErrMsg());
        }
    }

    @Override
    public void deregisterService(String serviceName, InetSocketAddress inetSocketAddress) {
        checkServiceInfo(serviceName, inetSocketAddress);
        try {
            final List<Instance> instances = serviceProvider.getAllInstance(serviceName);
            InetSocketAddress address;
            for (Instance instance : instances) {
                address = new InetSocketAddress(instance.getIp(), instance.getPort());
                if (Objects.equals(address, inetSocketAddress)) {
                    serviceProvider.deregisterInstance(serviceName, address.getHostName(), address.getPort());
                }
            }
        } catch (NacosException e) {
            log.error("Error occurred when deregistering service {}.\nError:code:{},msg:{}.",
                    serviceName, e.getErrCode(), e.getErrMsg());
        }
    }

    @Override
    public void deregisterAllService(){
        try {
            serviceProvider.clearAllRegistries();
        } catch (NacosException e) {
            log.error("Deregister service failed:{},{}",e.getErrCode(),e.getErrMsg(),e);
        }
    }

    private void checkServiceInfo(String serviceName, InetSocketAddress inetSocketAddress) {
        if (StringUtils.isBlank(serviceName) || isInValidSocketAddress(inetSocketAddress)) {
            throw new IllegalArgumentException("Invalid inetSocketAddress.");
        }
    }

    private boolean isInValidSocketAddress(InetSocketAddress address) {
        return address == null || StringUtils.isBlank(address.getHostName());
    }
}
