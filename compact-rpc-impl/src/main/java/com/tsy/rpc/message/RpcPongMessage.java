package com.tsy.rpc.message;

import com.tsy.rpc.base.message.Message;

/**
 * @author Steven.T
 * @date 2022/3/29
 */
public class RpcPongMessage extends Message {
    public RpcPongMessage() {
        super.setType(MessageType.RPC_PONG_MESSAGE.getType());
    }
}
