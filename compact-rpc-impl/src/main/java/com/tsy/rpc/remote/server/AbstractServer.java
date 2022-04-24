package com.tsy.rpc.remote.server;

import com.tsy.rpc.base.factory.SingletonFactory;
import com.tsy.rpc.config.RpcServiceInfo;
import com.tsy.rpc.manager.ServiceManager;
import com.tsy.rpc.manager.impl.DefaultServiceManager;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Steven.T
 * @date 2022/2/22
 */
@Slf4j
public abstract class AbstractServer {

    protected final ServiceManager serviceManager;

    protected AbstractServer() {
        serviceManager = SingletonFactory.getInstance(DefaultServiceManager.class);
    }

    /**
     * 启动时注册服务
     *
     * @param config 相关配置
     */
    public void registerServiceOnStart(RpcServiceInfo config) {
        serviceManager.publishService(config);
    }

    /**
     * 主启动方法
     */
    public abstract void run();
}
