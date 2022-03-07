package com.tsy.rpc.base.exception;

/**
 * @author Steven.T
 * @date 2022/2/24
 */
public class AnnotationNotFoundException extends RuntimeException{
    public AnnotationNotFoundException() {
    }

    public AnnotationNotFoundException(String message) {
        super(message);
    }

    public AnnotationNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
