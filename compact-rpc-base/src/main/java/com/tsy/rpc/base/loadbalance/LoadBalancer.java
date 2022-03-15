package com.tsy.rpc.base.loadbalance;

import com.tsy.rpc.base.extension.annotation.SPI;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * @author Steven.T
 * @date 2022/2/21
 */
@SPI
public interface LoadBalancer {
    /**
     * 负载均衡实现服务选择
     * @param addresses 服务地址列表
     * @return 服务地址
     */
    InetSocketAddress selectService(List<InetSocketAddress>addresses);
}
