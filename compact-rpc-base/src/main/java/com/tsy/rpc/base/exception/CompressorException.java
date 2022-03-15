package com.tsy.rpc.base.exception;

/**
 * @author Steven.T
 * @date 2022/3/15
 */
public class CompressorException extends RuntimeException{
    public CompressorException() {
    }

    public CompressorException(String message) {
        super(message);
    }

    public CompressorException(String message, Throwable cause) {
        super(message, cause);
    }

    public CompressorException(Throwable cause) {
        super(cause);
    }
}
