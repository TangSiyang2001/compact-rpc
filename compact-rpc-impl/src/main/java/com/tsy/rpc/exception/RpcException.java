package com.tsy.rpc.exception;

/**
 * @author Steven.T
 * @date 2022/3/15
 */
public class RpcException extends RuntimeException{
    public RpcException() {
    }

    public RpcException(String message) {
        super(message);
    }

    public RpcException(String message, Throwable cause) {
        super(message, cause);
    }

    public RpcException(Throwable cause) {
        super(cause);
    }

}
