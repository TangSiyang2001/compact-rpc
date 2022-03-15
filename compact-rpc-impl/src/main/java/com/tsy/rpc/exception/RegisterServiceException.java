package com.tsy.rpc.exception;

/**
 * @author Steven.T
 * @date 2022/3/14
 */
public class RegisterServiceException extends RuntimeException{
    public RegisterServiceException() {
    }

    public RegisterServiceException(String message) {
        super(message);
    }

    public RegisterServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public RegisterServiceException(Throwable cause) {
        super(cause);
    }
}
