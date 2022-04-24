package com.tsy.rpc.base.register;

import com.tsy.rpc.base.extension.annotation.SPI;

import java.net.InetSocketAddress;

/**
 * @author Steven.T
 * @date 2022/2/19
 */
@SPI
public interface ServiceRegistry {

    /**
     * 注册服务
     *
     * @param serviceName       主机名
     * @param inetSocketAddress 主机地址
     */
    void registerService(String serviceName, InetSocketAddress inetSocketAddress);

    /**
     * 取消服务注册
     *
     * @param serviceName       主机名
     * @param inetSocketAddress 主机地址
     */
    void deregisterService(String serviceName, InetSocketAddress inetSocketAddress);

    /**
     * 移除所有注册的服务
     */
    void deregisterAllService();
}
