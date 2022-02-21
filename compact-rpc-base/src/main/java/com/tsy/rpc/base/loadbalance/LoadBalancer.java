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
    InetSocketAddress selectService(List<InetSocketAddress>addresses);
}
