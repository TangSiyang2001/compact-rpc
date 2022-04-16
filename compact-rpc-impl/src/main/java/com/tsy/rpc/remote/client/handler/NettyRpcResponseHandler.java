package com.tsy.rpc.remote.client.handler;

import com.tsy.rpc.base.factory.SingletonFactory;
import com.tsy.rpc.message.RpcResponse;
import com.tsy.rpc.remote.client.UnFinishedRequestPool;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Steven.T
 * @date 2022/3/16
 */
@Slf4j
public class NettyRpcResponseHandler extends SimpleChannelInboundHandler<RpcResponse> {

    private final UnFinishedRequestPool requestPool;

    public NettyRpcResponseHandler() {
        this.requestPool = SingletonFactory.getInstance(UnFinishedRequestPool.class);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcResponse msg) {
        requestPool.signal(msg);
        ReferenceCountUtil.release(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("Exception occurred to client end.", cause);
        ctx.close();
    }
}
