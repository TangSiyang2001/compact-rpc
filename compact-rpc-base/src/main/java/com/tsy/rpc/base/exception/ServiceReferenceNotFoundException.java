package com.tsy.rpc.base.exception;

/**
 * @author Steven.T
 * @date 2022/2/22
 */
public class ServiceReferenceNotFoundException extends RuntimeException{
    public ServiceReferenceNotFoundException() {
    }

    public ServiceReferenceNotFoundException(String message) {
        super(message);
    }

    public ServiceReferenceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
