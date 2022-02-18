package com.tsy.rpc.base.exception;

/**
 * @author Steven.T
 * @date 2022/2/18
 */
public class NoSuchExtensionException extends RuntimeException{
    public NoSuchExtensionException() {
        this("No such an extension");
    }

    public NoSuchExtensionException(String message) {
        super(message);
    }

    protected NoSuchExtensionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
