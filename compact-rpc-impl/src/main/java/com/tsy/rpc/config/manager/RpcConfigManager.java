package com.tsy.rpc.config.manager;

import com.tsy.rpc.base.config.ConfigManager;
import com.tsy.rpc.config.RpcConfig;

/**
 * @author Steven.T
 * @date 2022/4/24
 */
public class RpcConfigManager implements ConfigManager {

    private RpcConfig rpcConfig;

    @Override
    public String getCodecAlgorithm() {
        return "protostuff";
    }

    @Override
    public void loadConfig() {

    }
}
