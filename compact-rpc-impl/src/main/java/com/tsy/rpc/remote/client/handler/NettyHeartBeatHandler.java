package com.tsy.rpc.remote.client.handler;

import com.tsy.rpc.message.RpcPingMessage;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelFutureListener;
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
public class NettyHeartBeatHandler extends ChannelDuplexHandler {
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            final IdleState state = ((IdleStateEvent) evt).state();
            if (Objects.equals(state, IdleState.WRITER_IDLE)) {
                //长时间未写入，发送心跳包
                RpcPingMessage rpcPingMessage = new RpcPingMessage();
                rpcPingMessage.setSequenceId(0);
                ctx.writeAndFlush(rpcPingMessage).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
            }
        }else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("Exception occurred", cause);
        ctx.close();
    }
}
