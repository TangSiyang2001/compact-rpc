package com.tsy.rpc.base.exception;

/**
 * @author Steven.T
 * @date 2022/2/21
 */
public class SerializationException extends RuntimeException{
    public SerializationException(String message) {
        super(message);
    }

    public SerializationException(String message, Throwable cause) {
        super(message, cause);
    }
}
