package com.tsy.rpc.base.exception;

/**
 * @author Steven.T
 * @date 2022/2/20
 */
public class LoadServiceException extends RuntimeException {
    public LoadServiceException(String message) {
        super(message);
    }

    public LoadServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoadServiceException(Throwable cause) {
        super(cause);
    }
}
