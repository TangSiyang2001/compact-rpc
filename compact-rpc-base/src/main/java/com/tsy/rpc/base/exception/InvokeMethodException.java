package com.tsy.rpc.base.exception;

/**
 * 执行所需方法反射调用异常
 *
 * @author Steven.T
 * @date 2022/3/7
 */
public class InvokeMethodException extends RuntimeException{
    public InvokeMethodException() {
    }

    public InvokeMethodException(String message) {
        super(message);
    }

    public InvokeMethodException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvokeMethodException(Throwable cause) {
        super(cause);
    }
}
