package com.tsy.rpc.config.manager;

import com.tsy.rpc.base.config.ConfigManager;
import com.tsy.rpc.base.exception.ConfigurationException;
import com.tsy.rpc.config.RpcConfig;
import lombok.extern.slf4j.Slf4j;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Properties;

/**
 * @author Steven.T
 * @date 2022/4/24
 */
@Slf4j
public class RpcConfigManager implements ConfigManager {

    private static final String DEFAULT_CONFIG_YAML = "rpc.yml";

    private static final String DEFAULT_CONFIG_PROPERTIES = "/rpc.properties";

    private final ClassLoader classLoader;

    private RpcConfig rpcConfig;

    public RpcConfigManager() {
        this.classLoader = this.getClass().getClassLoader();
        loadConfig();
    }

    @Override
    public String getCodecType() {
        return rpcConfig.getCodecType();
    }

    @Override
    public String getCompressType() {
        return rpcConfig.getCompressType();
    }

    @Override
    public int getServerPort() {
        return rpcConfig.getServerPort();
    }

    @Override
    public String getRegistryHost() {
        return rpcConfig.getRegistryHost();
    }

    @Override
    public int getRegistryPort() {
        return rpcConfig.getRegistryPort();
    }

    /**
     * 默认{@link com.tsy.rpc.config.manager.RpcConfigManager#DEFAULT_CONFIG_YAML}为配置文件
     */
    @Override
    public void loadConfig() {
        InputStream is = this.classLoader.getResourceAsStream(DEFAULT_CONFIG_YAML);
        try {
            if (is != null) {
                loadFromYml(is);
                is.close();
                return;
            }
            is = this.classLoader.getResourceAsStream(DEFAULT_CONFIG_PROPERTIES);
            if (is != null) {
                loadFromProperties(is);
                is.close();
                return;
            }
            this.rpcConfig = RpcConfig.DEFAULT;
        } catch (Exception e) {
            log.error("Loading configuration failed.", e);
            throw new ConfigurationException(e);
        }
    }

    private void loadFromYml(InputStream stream) {
        final Yaml yaml = new Yaml();
        this.rpcConfig = yaml.loadAs(stream, RpcConfig.class);
        if (rpcConfig == null) {
            throw new ConfigurationException();
        }
    }

    @SuppressWarnings("all")
    private void loadFromProperties(InputStream stream) throws IOException, IllegalAccessException {
        final Properties properties = new Properties();
        properties.load(stream);
        final Class<RpcConfig> rpcConfigClass = RpcConfig.class;
        final Field[] fields = rpcConfigClass.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            field.set(this.rpcConfig, properties.get(field.getName()));
        }
    }
}
