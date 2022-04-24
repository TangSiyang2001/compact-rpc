package com.tsy.rpc.base.config;

/**
 * 用于配置管理
 *
 * @author Steven.T
 * @date 2022/4/24
 */
public interface ConfigManager {
    //TODO:Impl包留下property、xml等实现，spring中加入application.properties,application.yml的实现

    void loadConfig();

    String getCodecAlgorithm();
}
