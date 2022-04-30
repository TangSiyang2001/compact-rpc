package com.tsy.rpc.base.exception;

/**
 * 配置过程异常
 *
 * @author Steven.T
 * @date 2022/4/28
 */
public class ConfigurationException extends RuntimeException{
    public ConfigurationException() {
    }

    public ConfigurationException(String message) {
        super(message);
    }

    public ConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfigurationException(Throwable cause) {
        super(cause);
    }
}
