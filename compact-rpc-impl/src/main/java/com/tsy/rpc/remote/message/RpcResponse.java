package com.tsy.rpc.remote.message;

import com.tsy.rpc.base.message.Message;
import com.tsy.rpc.base.message.MessageType;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Steven.T
 * @date 2022/2/19
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RpcResponse extends Message {

    private Object value;

    private Exception exception;

    public RpcResponse(Object value) {
        super.setType(MessageType.RPC_RESPONSE);
        this.value = value;
    }

    public RpcResponse(Exception exception){
        super.setType(MessageType.RPC_RESPONSE);
        this.exception = exception;
    }
}
