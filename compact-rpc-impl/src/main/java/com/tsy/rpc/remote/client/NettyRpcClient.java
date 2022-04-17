package com.tsy.rpc.remote.client;

import com.tsy.rpc.base.extension.ExtensionLoader;
import com.tsy.rpc.base.factory.SingletonFactory;
import com.tsy.rpc.base.register.ServiceDiscovery;
import com.tsy.rpc.codec.MessageSharableCodec;
import com.tsy.rpc.codec.ProtocolFrameDecoder;
import com.tsy.rpc.exception.RpcException;
import com.tsy.rpc.message.RpcRequest;
import com.tsy.rpc.message.RpcResponse;
import com.tsy.rpc.remote.client.handler.NettyHeartBeatHandler;
import com.tsy.rpc.remote.client.handler.NettyRpcResponseHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.concurrent.CompletableFuture;

/**
 * @author Steven.T
 * @date 2022/3/16
 */
@Slf4j
public class NettyRpcClient implements RequestSender {

    /**
     * 事件循环
     */
    private final EventLoopGroup eventLoopGroup;

    /**
     * 客户端引导
     */
    private final Bootstrap bootstrap;

    /**
     * 服务发现
     */
    private final ServiceDiscovery serviceDiscovery;

    private final UnFinishedRequestPool unFinishedRequestPool;

    private final ChannelPool channelPool;

    public NettyRpcClient() {
        this.eventLoopGroup = new NioEventLoopGroup();
        this.serviceDiscovery = ExtensionLoader.getExtensionLoader(ServiceDiscovery.class).getExtension("nacos");
        this.unFinishedRequestPool = SingletonFactory.getInstance(UnFinishedRequestPool.class);
        this.channelPool = SingletonFactory.getInstance(ChannelPool.class);
        this.bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
                .handler(new LoggingHandler(LogLevel.INFO))
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        final ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new IdleStateHandler(0, 5, 0));
                        pipeline.addLast(new ProtocolFrameDecoder());
                        pipeline.addLast(new MessageSharableCodec());
                        pipeline.addLast(new NettyHeartBeatHandler());
                        pipeline.addLast(new NettyRpcResponseHandler());
                    }
                });
    }

    @Override
    public CompletableFuture<RpcResponse> send(RpcRequest request) {
        final InetSocketAddress address = serviceDiscovery.discoverService(request.getServiceName());
        if (address == null) {
            throw new RpcException("Service address for service" + request.getServiceName()
                    + "not found,requestId" + request.getRequestId()
            );
        }
        final CompletableFuture<RpcResponse> responseFuture = new CompletableFuture<>();
        final Channel channel = getChannel(address);
        if (!channel.isActive()) {
            throw new RpcException("Connection failed.");
        }
        if (!channel.isWritable()) {
            throw new IllegalStateException("Channel cannot be written");
        }
        unFinishedRequestPool.wait(request.getRequestId(), responseFuture);
        channel.writeAndFlush(request).addListener((ChannelFutureListener) future -> {
            if (future.isSuccess()) {
                log.info("{}@{} sent successfully.", request.getServiceName(), request.getRequestId());
            } else {
                responseFuture.completeExceptionally(future.cause());
                log.error("{}@{} sent unsuccessfully.", request.getServiceName(), request.getRequestId());
                future.channel().close();
            }
        });
        return responseFuture;
    }

    /**
     * 获取到服务端的连接
     *
     * @param serverAddress 服务端地址
     * @return 连接
     */
    private Channel getChannel(InetSocketAddress serverAddress) {
        final Channel channel = channelPool.getChannel(serverAddress);
        if (channel != null) {
            return channel;
        }
        final Channel newChannel = connect(serverAddress);
        channelPool.putChannel(serverAddress, newChannel);
        return newChannel;
    }

    /**
     * 连接到服务端
     *
     * @param serverAddress 服务端地址
     * @return 连接
     */
    private Channel connect(InetSocketAddress serverAddress) {
        final ChannelFuture channelFuture = bootstrap.connect(serverAddress).addListener(future -> {
            if (future.isSuccess()) {
                log.info("Connection success to {}", serverAddress);
            } else {
                throw new RpcException("Connection failed to {" + serverAddress + "}", future.cause());
            }
        });
        try {
            //记住返回的是等待连接成功后的结果 channelFuture.sync().channel()而非channelFuture.channel()
            return channelFuture.sync().channel();
        } catch (InterruptedException e) {
            throw new RpcException("Connect process error.", e);
        }
    }

    public void close() {
        eventLoopGroup.shutdownGracefully();
    }
}
