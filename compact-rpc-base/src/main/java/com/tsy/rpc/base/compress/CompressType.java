package com.tsy.rpc.base.compress;

import lombok.AllArgsConstructor;
import lombok.Getter;

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
    GZIP((byte) 0x00,"gzip");

    /**
     * 压缩类型
     */
    private final byte type;

    /**
     * 压缩方式名
     */
    private final String name;

    public static String getCompressName(byte compressType){
        for (CompressType type : CompressType.values()) {
            if(type.getType() == compressType){
                return type.getName();
            }
        }
        return null;
    }
}
