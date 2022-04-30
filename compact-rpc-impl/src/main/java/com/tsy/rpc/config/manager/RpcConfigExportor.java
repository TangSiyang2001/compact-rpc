package com.tsy.rpc.config.manager;

import com.tsy.rpc.base.config.ConfigManager;
import com.tsy.rpc.base.factory.SingletonFactory;

/**
 * @author Steven.T
 * @date 2022/4/29
 */
public class RpcConfigExportor {
    private static final ConfigManager CONFIG;

    static {
        CONFIG = SingletonFactory.getInstance(RpcConfigManager.class);
    }

    private RpcConfigExportor() {
        throw new IllegalStateException("This is a util class");
    }

    public static String getCodecType() {
        return CONFIG.getCodecType();
    }

    public static String getCompressType() {
        return CONFIG.getCompressType();
    }

    public static int getServerPort() {
        return CONFIG.getServerPort();
    }

    public static String getRegistryHost() {
        return CONFIG.getRegistryHost();
    }

    public static int getRegistryPort() {
        return CONFIG.getRegistryPort();
    }
}
