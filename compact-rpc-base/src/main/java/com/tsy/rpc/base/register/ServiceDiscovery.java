package com.tsy.rpc.base.register;

import com.tsy.rpc.base.extension.annotation.SPI;

import java.net.InetSocketAddress;

/**
 * @author Steven.T
 * @date 2022/2/19
 */
@SPI
public interface ServiceDiscovery {

    /**
     * 服务发现
     * @param serviceName 服务名
     * @return 主机信息
     */
    InetSocketAddress discoverService(String serviceName);
}
