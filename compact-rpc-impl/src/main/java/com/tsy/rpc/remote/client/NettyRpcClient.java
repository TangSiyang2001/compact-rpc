package com.tsy.rpc.remote.client;

import com.tsy.rpc.base.extension.ExtensionLoader;
import com.tsy.rpc.base.factory.SingletonFactory;
import com.tsy.rpc.base.register.ServiceDiscovery;
import com.tsy.rpc.codec.MessageSharableCodec;
import com.tsy.rpc.exception.RpcException;
import com.tsy.rpc.message.RpcRequest;
import com.tsy.rpc.message.RpcResponse;
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

    private final WaitPool waitPool;

    public NettyRpcClient() {
        this.eventLoopGroup = new NioEventLoopGroup();
        this.serviceDiscovery = ExtensionLoader.getExtensionLoader(ServiceDiscovery.class).getExtension("nacos");
        this.waitPool = SingletonFactory.getInstance(WaitPool.class);
        this.bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
                .handler(new LoggingHandler(LogLevel.INFO))
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        final ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new IdleStateHandler(0, 5, 0));
                        pipeline.addLast(new MessageSharableCodec());
                        //TODO:编写并添加response handler
                    }
                });
    }

    @Override
    public CompletableFuture<RpcResponse> send(RpcRequest request) {
        final CompletableFuture<RpcResponse> responseFuture = new CompletableFuture<>();
        //TODO:获取连接,发送请求,返回future
        return null;
    }

    /**
     * 获取到服务端的连接
     *
     * @param serverAddress 服务端地址
     * @return 连接
     */
    private Channel getChannel(InetSocketAddress serverAddress) {
        //TODO:设计成先从连接缓存池中取，没有的话发起连接，并缓存
        return connect(serverAddress);
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
        return channelFuture.channel();
    }

    public void close() {
        eventLoopGroup.shutdownGracefully();
    }
}
