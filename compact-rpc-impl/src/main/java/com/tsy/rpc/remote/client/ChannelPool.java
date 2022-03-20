package com.tsy.rpc.remote.client;

import io.netty.channel.Channel;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 连接池
 *
 * @author Steven.T
 * @date 2022/3/20
 */
public class ChannelPool {
    /**
     * 连接缓存
     */
    private final Map<String, Channel> channelCache;

    public ChannelPool() {
        this.channelCache = new ConcurrentHashMap<>(64);
    }

    /**
     * 放入连接
     *
     * @param address 连接地址
     * @param channel 连接
     */
    public void putChannel(InetSocketAddress address, Channel channel) {
        channelCache.put(address.toString(), channel);
    }

    /**
     * 获取连接并同时移除无效连接，由于RPC框架连接较为固定，不考虑缓存膨胀
     *
     * @param address 连接地址
     * @return 连接
     */
    public Channel getChannel(InetSocketAddress address) {
        final String channelName = address.toString();
        final Channel channel = channelCache.get(channelName);
        if (channel != null) {
            if (channel.isActive()) {
                return channel;
            } else {
                channelCache.remove(channelName);
            }
        }
        return null;
    }

    public void removeChannel(InetSocketAddress address) {
        channelCache.remove(address.toString());
    }
}
