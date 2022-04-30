package com.tsy.rpc.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

/**
 * @author Steven.T
 * @date 2022/2/20
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RpcConfig {

    public static final RpcConfig DEFAULT;
    private static final String DEFAULT_CODEC_TYPE = "protostuff";
    private static final String DEFAULT_COMPRESS_TYPE = "gzip";
    private static final int DEFAULT_SERVER_PORT = 8888;
    private static final String DEFAULT_REGISTRY_HOST = "127.0.0.1";
    private static final int DEFAULT_REGISTRY_PORT = 8848;

    static {
        DEFAULT = RpcConfig.builder()
                .codecType(DEFAULT_CODEC_TYPE)
                .compressType(DEFAULT_COMPRESS_TYPE)
                .serverPort(DEFAULT_SERVER_PORT)
                .registryHost(DEFAULT_REGISTRY_HOST)
                .registryPort(DEFAULT_REGISTRY_PORT)
                .build();
    }

    private String codecType;
    private String compressType;
    private Integer serverPort;
    private String registryHost;
    private Integer registryPort;


    public String getCodecType() {
        return Optional.ofNullable(codecType).orElse(DEFAULT_CODEC_TYPE);
    }

    public String getCompressType() {
        return Optional.ofNullable(compressType).orElse(DEFAULT_COMPRESS_TYPE);
    }

    public Integer getServerPort() {
        return Optional.ofNullable(serverPort).orElse(DEFAULT_SERVER_PORT);
    }

    public String getRegistryHost() {
        return Optional.ofNullable(registryHost).orElse(DEFAULT_REGISTRY_HOST);
    }

    public Integer getRegistryPort() {
        return Optional.ofNullable(registryPort).orElse(DEFAULT_REGISTRY_PORT);
    }
}
