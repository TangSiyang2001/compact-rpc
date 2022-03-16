package com.tsy.rpc.remote.client;

import com.tsy.rpc.message.RpcResponse;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Steven.T
 * @date 2022/3/16
 */
public class WaitPool {

    private static final Map<String, CompletableFuture<RpcResponse>> FUTURE_CACHE = new ConcurrentHashMap<>(64);

    /**
     * 缓存请求的CompletableFuture
     *
     * @param requestId 请求id
     * @param future    请求的CompletableFuture
     */
    public void wait(String requestId, CompletableFuture<RpcResponse> future) {
        FUTURE_CACHE.put(requestId, future);
    }

    /**
     * 移除缓存,并将收到的response放入CompletableFuture中
     *
     * @param response 响应
     */
    public void signal(RpcResponse response) {
        final CompletableFuture<RpcResponse> future = FUTURE_CACHE.remove(response.getRequestId());
        if (future == null) {
            throw new IllegalStateException("Cannot signal a future that does not exist.");
        }
        future.complete(response);
    }
}
