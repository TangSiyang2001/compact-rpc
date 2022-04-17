package com.tsy.rpc.remote.server.handler;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * @author Steven.T
 * @date 2022/3/29
 */
@Slf4j
public class NettyPingMessageHandler extends ChannelDuplexHandler {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("NettyPingMessageHandler active");
        super.channelRead(ctx, msg);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            final IdleState state = ((IdleStateEvent) evt).state();
            if (Objects.equals(state, IdleState.READER_IDLE)) {
                log.info("Idle happened,close the connection.");
                ctx.close();
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("Exception occurred to server end.", cause);
        ctx.close();
    }
}
