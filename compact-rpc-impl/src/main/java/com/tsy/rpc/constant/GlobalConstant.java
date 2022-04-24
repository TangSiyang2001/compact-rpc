package com.tsy.rpc.constant;

/**
 * @author Steven.T
 * @date 2022/2/20
 */
public class GlobalConstant {
    private GlobalConstant(){
        throw new IllegalStateException("This is a constant class");
    }

    public static final byte PROTOCOL_VERSION = 1;

    public static final int DEFAULT_RPC_SERVICE_PORT = 8888;

}
