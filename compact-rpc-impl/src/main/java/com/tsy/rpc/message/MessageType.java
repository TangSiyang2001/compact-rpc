package com.tsy.rpc.message;

import com.tsy.rpc.base.message.Message;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Steven.T
 * @date 2022/2/19
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum MessageType {
    /**
     * rpc-request
     */
    RPC_REQUEST((byte) 0x01,RpcRequest.class),
    /**
     * prc-response
     */
    RPC_RESPONSE((byte) 0x02,RpcResponse.class);

    byte type;

    Class<? extends Message> clazz;

    public static Class<? extends Message> getMessageClass(byte messageType){
        for (MessageType value : MessageType.values()) {
            if(value.getType() == messageType){
                return value.getClazz();
            }
        }
        return null;
    }
}
