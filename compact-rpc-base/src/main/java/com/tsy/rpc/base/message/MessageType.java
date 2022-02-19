package com.tsy.rpc.base.message;

/**
 * @author Steven.T
 * @date 2022/2/19
 */
public final class MessageType {

    public static final byte RPC_REQUEST = (byte) 0x01;
    public static final byte RPC_RESPONSE = (byte) 0x02;

    private MessageType() {
        throw new IllegalStateException("This is a constant class");
    }

}
