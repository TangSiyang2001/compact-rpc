package com.tsy.rpc.message;

import com.tsy.rpc.base.message.Message;

/**
 * @author Steven.T
 * @date 2022/3/29
 */
public class RpcPingMessage extends Message {
    public RpcPingMessage(){
        super.setType(MessageType.RPC_PING_MESSAGE.getType());
    }
}
