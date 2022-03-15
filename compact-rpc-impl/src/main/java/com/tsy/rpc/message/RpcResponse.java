package com.tsy.rpc.message;

import com.tsy.rpc.base.message.Message;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Steven.T
 * @date 2022/2/19
 */
@Data
@Builder
@AllArgsConstructor
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

}
