package com.tsy.rpc.remote.server;

import com.tsy.rpc.base.runtime.RpcRuntime;
import com.tsy.rpc.base.thread.ThreadPoolManager;
import com.tsy.rpc.codec.MessageSharableCodec;
import com.tsy.rpc.codec.ProtocolFrameDecoder;
import com.tsy.rpc.constant.GlobalConstant;
import com.tsy.rpc.remote.server.handler.NettyPingMessageHandler;
import com.tsy.rpc.remote.server.handler.NettyRpcRequestHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.DefaultEventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author Steven.T
 * @date 2022/2/24
 */
@Slf4j
public class NettyRpcServer extends AbstractServer {

    @Override
    public void start() {
        final NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        final NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        final DefaultEventLoopGroup serviceHandlerGroup = new DefaultEventLoopGroup(
                RpcRuntime.cpus() * 2,
                ThreadPoolManager.getManager("service-handler-group").createThreadPool()
        );
        try {
            final ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap
                    .group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.DEBUG))
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        // TODO:自定义handler
                        @Override
                        public void initChannel(SocketChannel ch) {
                            ch.pipeline()
                                    //TODO:加入配置项
                                    .addLast(new IdleStateHandler(30, 0, 0, TimeUnit.SECONDS))
                                    .addLast(new ProtocolFrameDecoder())
                                    .addLast(new MessageSharableCodec())
                                    .addLast(new NettyPingMessageHandler())
                                    .addLast(serviceHandlerGroup, new NettyRpcRequestHandler());
                        }
                    });
            final ChannelFuture channelFuture = serverBootstrap.bind(GlobalConstant.RPC_SERVICE_PORT).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("Error occurs when starting server:", e);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
