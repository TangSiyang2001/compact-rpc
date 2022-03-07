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

    private int sequenceId;

    private Object value;

    private Exception exception;

    public RpcResponse(Object value) {
        super.setType(MessageType.RPC_RESPONSE.getType());
        this.value = value;
    }

    public RpcResponse(Exception exception){
        super.setType(MessageType.RPC_RESPONSE.getType());
        this.exception = exception;
    }
}
