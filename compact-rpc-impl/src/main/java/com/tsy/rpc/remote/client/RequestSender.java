package com.tsy.rpc.remote.client;

import com.tsy.rpc.base.extension.annotation.SPI;
import com.tsy.rpc.message.RpcRequest;
import com.tsy.rpc.message.RpcResponse;

import java.util.concurrent.CompletableFuture;

/**
 * 发送消息的接口
 *
 * @author Steven.T
 * @date 2022/3/14
 */
@SPI
public interface RequestSender {

    /**
     * 发送请求
     *
     * @param request 请求
     * @return CompletableFuture 当客户端接收到服务端响应时会将相关响应设置到相应CompletableFuture中。
     */
    CompletableFuture<RpcResponse> send(RpcRequest request);

}
