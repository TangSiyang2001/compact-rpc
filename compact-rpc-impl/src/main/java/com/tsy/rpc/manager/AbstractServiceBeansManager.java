package com.tsy.rpc.manager;

import com.tsy.rpc.base.exception.ServiceReferenceNotFoundException;
import com.tsy.rpc.base.register.ServiceRegistry;
import com.tsy.rpc.config.RpcServiceConfig;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 存储相关接口对应的服务实例
 *
 * @author Steven.T
 * @date 2022/2/22
 */
public abstract class AbstractServiceBeansManager implements ServiceBeansManager {
    /**
     * 服务实例缓存
     */
    private final Map<String, Object> servicesCache = new ConcurrentHashMap<>(128);

    protected ServiceRegistry serviceRegistry = null;

    @Override
    public Object getServiceInstance(String serviceName) {
        final Object service = servicesCache.get(serviceName);
        if (service == null) {
            throw new ServiceReferenceNotFoundException("Service reference not found for name " + serviceName);
        }
        return service;
    }


    @Override
    public void publishService(RpcServiceConfig config) {
        final String serviceName = config.getServiceName();
        registerService(serviceName);
        servicesCache.put(serviceName, config.getServiceInstance());
    }

    /**
     * 注册服务
     *
     * @param serviceName 服务名
     */
    public abstract void registerService(String serviceName);
}