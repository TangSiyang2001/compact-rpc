package com.tsy.rpc.manager.impl;

import com.tsy.rpc.base.extension.ExtensionLoader;
import com.tsy.rpc.base.register.ServiceRegistry;
import com.tsy.rpc.constant.GlobalConstant;
import com.tsy.rpc.manager.AbstractServiceBeansManager;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

/**
 * @author Steven.T
 * @date 2022/2/22
 */
@Slf4j
public class DefaultServiceBeansManager extends AbstractServiceBeansManager {

    public DefaultServiceBeansManager() {
        //TODO:记得添加spi
        super.serviceRegistry = ExtensionLoader.getExtensionLoader(ServiceRegistry.class).getExtension("nacos");
    }

    @Override
    public void registerService(String serviceName) {
        try {
            final String hostAddress = InetAddress.getLocalHost().getHostAddress();
            serviceRegistry.registerService(serviceName, new InetSocketAddress(hostAddress, GlobalConstant.RPC_SERVICE_PORT));
        } catch (UnknownHostException e) {
            log.error("Register service fail.UnknownHost.Caused by {}", e.getCause().getMessage());
        }
    }
}