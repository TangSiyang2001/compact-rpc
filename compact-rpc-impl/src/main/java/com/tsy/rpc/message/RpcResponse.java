package com.tsy.rpc.message;

import com.tsy.rpc.base.message.Message;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Steven.T
 * @date 2022/2/19
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class RpcResponse extends Message {

    /**
     * 验证响应合法性的请求id
     */
    private String requestId;

    private boolean success;

    private Object value;

    private Exception exception;

    public RpcResponse(Object value) {
        super.setType(MessageType.RPC_RESPONSE.getType());
        this.value = value;
    }

    public RpcResponse(Exception exception) {
        super.setType(MessageType.RPC_RESPONSE.getType());
        this.exception = exception;
    }

    /**
     * builder进行build操作时调用的是这个构造函数，要在里面指明类型
     * @param requestId 请求id
     * @param success 是否执行成功
     * @param value 返回值
     * @param exception 产生异常
     */
    public RpcResponse(String requestId, boolean success, Object value, Exception exception) {
        super.setType(MessageType.RPC_RESPONSE.getType());
        this.requestId = requestId;
        this.success = success;
        this.value = value;
        this.exception = exception;
    }
}
