package com.tsy.rpc.base.exception;

/**
 * @author Steven.T
 * @date 2022/2/24
 */
public class ThreadManageException extends RuntimeException{
    public ThreadManageException() {
    }

    public ThreadManageException(String message) {
        super(message);
    }

    public ThreadManageException(String message, Throwable cause) {
        super(message, cause);
    }
}
