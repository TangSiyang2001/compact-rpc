package com.tsy.rpc.remote.client.proxy;

import com.tsy.rpc.config.RpcServiceInfo;
import com.tsy.rpc.exception.RpcException;
import com.tsy.rpc.message.RpcRequest;
import com.tsy.rpc.message.RpcResponse;
import com.tsy.rpc.remote.client.RequestSender;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * 代理客户端调用，屏蔽消息发送细节
 *
 * @author Steven.T
 * @date 2022/3/14
 */
@Slf4j
public class ClientProxy implements InvocationHandler {

    private final RequestSender requestSender;

    private final RpcServiceInfo serviceInfo;

    public ClientProxy(RequestSender sender, RpcServiceInfo info) {
        this.requestSender = sender;
        this.serviceInfo = info;
    }

    /**
     * @param clazz 接口类型
     * @param <T>   相应的接口类型
     * @return 代理实例
     */
    @SuppressWarnings("unchecked")
    public <T> T getProxy(Class<T> clazz) {
        if (!clazz.isInterface()) {
            throw new IllegalArgumentException("Proxy type must be interface.");
        }
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, this);
    }

    /**
     * 执行代理逻辑
     *
     * @param proxy  代理对象
     * @param method 代理方法
     * @param args   方法参数
     * @return 执行结果
     * @throws Throwable 异常
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //构建请求
        final RpcRequest request = RpcRequest.builder()
                .requestId(UUID.randomUUID().toString())
                .interfaceName(serviceInfo.getServiceName())
                .methodName(method.getName())
                .paramTypes(method.getParameterTypes())
                .paramValues(args)
                .returnType(method.getReturnType())
                .version(serviceInfo.getVersion())
                .group(serviceInfo.getGroup())
                .serviceName(serviceInfo.getServiceName())
                .build();
//        final RpcRequest request = new RpcRequest(UUID.randomUUID().toString(), serviceInfo.getServiceName()
//                , method.getName(), method.getReturnType(), method.getParameterTypes(), args, serviceInfo.getVersion(), serviceInfo.getGroup(), serviceInfo.getServiceName());
        //发送请求
        final CompletableFuture<RpcResponse> responseFuture = requestSender.send(request);
        //得到结果
        final RpcResponse response = responseFuture.get();
        //处理返回结果
        return postProcessResponse(request, response);
    }

    private Object postProcessResponse(RpcRequest request, RpcResponse response) {
        checkResponse(request, response);
        if (response.isSuccess()) {
            return response.getValue();
        } else {
            return response.getException();
        }
    }

    private void checkResponse(RpcRequest request, RpcResponse response) {
        if (response == null) {
            log.error("Request {} does not receive a response", request.getSequenceId());
            throw new RpcException("Miss request.");
        }
        if (!Objects.equals(request.getRequestId(), response.getRequestId())) {
            log.error("Request {} and response {} do not match.", request.getRequestId(), response.getRequestId());
            throw new RpcException("Request and response do not match.");
        }
    }
}
