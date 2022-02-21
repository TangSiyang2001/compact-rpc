package com.tsy.rpc.constant;

/**
 * @author Steven.T
 * @date 2022/2/20
 */
public class Constant {
    private Constant(){
        throw new IllegalStateException("This is a constant class");
    }

    public static final byte[] MAGIC_CODE_CONTENT = {(byte)'_',(byte)'r',(byte)'p',(byte)'c'};

    public static final byte PROTOCOL_VERSION = 1;
}
