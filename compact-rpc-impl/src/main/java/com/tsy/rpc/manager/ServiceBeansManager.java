package com.tsy.rpc.manager;

import com.tsy.rpc.config.RpcServiceConfig;

/**
 * @author Steven.T
 * @date 2022/2/22
 */
public interface ServiceBeansManager {

    /**
     * 注册并缓存服务
     *
     * @param config 服务配置
     */
    void publishService(RpcServiceConfig config);

    /**
     * 获取服务实例
     *
     * @param serviceName 服务名
     * @return 服务
     */
    Object getServiceInstance(String serviceName);
}
