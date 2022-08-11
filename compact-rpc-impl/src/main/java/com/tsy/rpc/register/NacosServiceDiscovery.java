package com.tsy.rpc.register;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.tsy.rpc.base.extension.ExtensionLoader;
import com.tsy.rpc.base.factory.SingletonFactory;
import com.tsy.rpc.base.loadbalance.LoadBalancer;
import com.tsy.rpc.base.register.ServiceDiscovery;
import com.tsy.rpc.loadbalance.RandomBalancer;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Steven.T
 * @date 2022/2/20
 */
@Slf4j
public class NacosServiceDiscovery implements ServiceDiscovery {

    private LoadBalancer loadBalancer;

    private final NacosServiceAdapter serviceProvider = SingletonFactory.getInstance(NacosServiceAdapter.class);

    public NacosServiceDiscovery(){
        this.loadBalancer = ExtensionLoader.getExtensionLoader(LoadBalancer.class).getExtension("loadbalance");
    }

    public NacosServiceDiscovery(LoadBalancer loadBalancer) {
        this.loadBalancer = loadBalancer == null ? new RandomBalancer() : loadBalancer;
    }

    public void setLoadBalancer(LoadBalancer loadBalancer) {
        this.loadBalancer = loadBalancer == null ? new RandomBalancer() : loadBalancer;
    }

    @Override
    public InetSocketAddress discoverService(String serviceName) {
        try {
            final List<Instance> instances = serviceProvider.getAllInstance(serviceName);
            final List<InetSocketAddress> addresses = instances.stream()
                    .map(instance -> new InetSocketAddress(instance.getIp(), instance.getPort()))
                    .collect(Collectors.toList());
            return loadBalancer.selectService(addresses);
        } catch (NacosException e) {
            log.error("Error occurs when discovering service {}.\nError:code:{},msg:{}",
                    serviceName, e.getErrCode(), e.getErrMsg());
        }
        return null;
    }
}
