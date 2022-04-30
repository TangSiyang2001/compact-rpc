package com.tsy.rpc.manager.impl;

import com.tsy.rpc.base.extension.ExtensionLoader;
import com.tsy.rpc.base.register.ServiceRegistry;
import com.tsy.rpc.config.manager.RpcConfigExportor;
import com.tsy.rpc.exception.RegisterServiceException;
import com.tsy.rpc.manager.AbstractServiceManager;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

/**
 * 服务治理
 *
 * @author Steven.T
 * @date 2022/2/22
 */
@Slf4j
public class DefaultServiceManager extends AbstractServiceManager {

    private final ServiceRegistry serviceRegistrar;

    public DefaultServiceManager() {
        serviceRegistrar = ExtensionLoader.getExtensionLoader(ServiceRegistry.class).getExtension("nacos");
    }

    @Override
    public void registerService(String serviceName) {
        try {
            final String hostAddress = InetAddress.getLocalHost().getHostAddress();
            serviceRegistrar.registerService(serviceName, new InetSocketAddress(hostAddress, RpcConfigExportor.getServerPort()));
        } catch (UnknownHostException e) {
            log.error("Register service fail.UnknownHost.Caused by {}", e.getCause().getMessage());
            throw new RegisterServiceException("Register service fail.");
        }
    }

    @Override
    public void deregisterAllService() {
        serviceRegistrar.deregisterAllService();
    }
}
