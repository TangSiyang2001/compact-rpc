package com.tsy.rpc.spring.config;

import com.tsy.rpc.config.manager.RpcConfigManager;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;

/**
 * @author Steven.T
 * @date 2022/4/29
 */
public class SpringRpcConfigManager extends RpcConfigManager implements ResourceLoaderAware {

    private ResourceLoader resourceLoader;

    @Override
    public void loadConfig() {
        //TODO：待重写
        super.loadConfig();
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
}
