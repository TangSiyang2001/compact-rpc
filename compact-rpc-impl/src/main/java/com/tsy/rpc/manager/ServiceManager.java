package com.tsy.rpc.manager;

import com.tsy.rpc.config.RpcServiceInfo;

/**
 * @author Steven.T
 * @date 2022/2/22
 */
public interface ServiceManager {

    /**
     * 注册并缓存服务
     *
     * @param config 服务配置
     */
    void publishService(RpcServiceInfo config);

    /**
     * 获取服务实例
     *
     * @param serviceName 服务名
     * @return 服务
     */
    Object getServiceInstance(String serviceName);

    /**
     * 移除服务
     */
    void deregisterAllService();
}
