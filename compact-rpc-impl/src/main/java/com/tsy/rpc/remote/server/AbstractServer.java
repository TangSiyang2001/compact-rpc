package com.tsy.rpc.remote.server;

import com.tsy.rpc.base.factory.SingletonFactory;
import com.tsy.rpc.config.RpcServiceInfo;
import com.tsy.rpc.manager.ServiceBeansManager;
import com.tsy.rpc.manager.impl.DefaultServiceBeansManager;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Steven.T
 * @date 2022/2/22
 */
@Slf4j
public abstract class AbstractServer {

    private final ServiceBeansManager serviceBeansManager;
    /**
     * TODO:改为可配置
     */
    private int port = 8888;

    protected AbstractServer() {
        serviceBeansManager = SingletonFactory.getInstance(DefaultServiceBeansManager.class);
    }

    /**
     * 启动时注册服务
     *
     * @param config 相关配置
     */
    public void registerServiceOnStart(RpcServiceInfo config) {
        serviceBeansManager.publishService(config);
    }

    public abstract void start();
}
