package com.tsy.rpc.base.compress;

import com.tsy.rpc.base.exception.CompressorException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * @author Steven.T
 * @date 2022/2/20
 */
@Getter
@AllArgsConstructor
public enum CompressType {
    /**
     * gzip压缩方式
     */
    GZIP((byte) 0x00, "gzip");

    /**
     * 压缩类型
     */
    private final byte type;

    /**
     * 压缩方式名
     */
    private final String name;

    public static String getCompressName(byte compressType) {
        for (CompressType type : CompressType.values()) {
            if (type.getType() == compressType) {
                return type.getName();
            }
        }
        return null;
    }

    public static byte getCompressType(String name) {
        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException("Codec name cannot be blank.");
        }
        for (CompressType value : CompressType.values()) {
            if (Objects.equals(value.getName(), name)) {
                return value.getType();
            }
        }
        throw new CompressorException("Compressor name does not match a type.");
    }
}
