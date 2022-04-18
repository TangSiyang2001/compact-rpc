package com.tsy.rpc.remote.server.handler;

import com.tsy.rpc.base.exception.InvokeMethodException;
import com.tsy.rpc.base.factory.SingletonFactory;
import com.tsy.rpc.manager.ServiceBeansManager;
import com.tsy.rpc.manager.impl.DefaultServiceBeansManager;
import com.tsy.rpc.message.RpcRequest;
import com.tsy.rpc.message.RpcResponse;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Steven.T
 * @date 2022/2/24
 */
@Slf4j
public class NettyRpcRequestHandler extends SimpleChannelInboundHandler<RpcRequest> {

    private final ServiceBeansManager serviceBeansManager = SingletonFactory.getInstance(DefaultServiceBeansManager.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequest msg) {
        log.info("received message{}",msg);
        final String serviceName = msg.getServiceName();
        final Object result = doInvoke(msg, serviceName);
        final RpcResponse response = buildResponse(msg.getSequenceId(), msg.getRequestId(), result);
        if (ctx.channel().isActive() && ctx.channel().isWritable()) {
            ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
        }
    }

    private Object doInvoke(RpcRequest msg, String serviceName) {
        //获取服务实例
        final Object serviceInstance = serviceBeansManager.getServiceInstance(serviceName);
        //反射调用，得到服务结果
        try {
            final Method method = serviceInstance.getClass().getMethod(msg.getMethodName(), msg.getParamTypes());
            return method.invoke(serviceInstance, msg.getParamValues());
        } catch (NoSuchMethodException e) {
            log.error("Method does not exist.");
            throw new InvokeMethodException("Method does not exist.");
        } catch (IllegalAccessException e) {
            log.error("Method is not accessible.");
            throw new InvokeMethodException("Method is not accessible.");
        } catch (InvocationTargetException e) {
            log.error("Target method execution error.");
            throw new InvokeMethodException("Target method execution error.");
        }
    }

    private RpcResponse buildResponse(int sequenceId, String requestId, Object data) {
        RpcResponse response;
        if (data instanceof Exception) {
            response = RpcResponse
                    .builder()
                    .requestId(requestId)
                    .success(false)
                    .exception((Exception) data)
                    .build();

        } else {
            response = RpcResponse
                    .builder()
                    .requestId(requestId)
                    .success(true)
                    .value(data)
                    .build();
        }
        response.setSequenceId(sequenceId);
        return response;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("Exception occurred to server end.", cause);
        ctx.close();
    }

}
