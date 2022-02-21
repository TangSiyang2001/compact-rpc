package com.tsy.rpc.base.serialize;

import com.tsy.rpc.base.exception.CodecException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * @author Steven.T
 * @date 2022/2/20
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum CodecType {

    /**
     * protostuff
     */
    PROTOSTUFF((byte) 0x00, "protostuff");

    private final byte type;

    private final String name;

    public static String getCodecName(byte codecType){
        for (CodecType value : CodecType.values()) {
            if(codecType == value.getType()){
                return value.getName();
            }
        }
        return null;
    }

    public static byte getCodecType(String name){
        if(StringUtils.isBlank(name)){
            throw new IllegalArgumentException("Codec name cannot be blank.");
        }
        for (CodecType value : CodecType.values()) {
            if(Objects.equals(value.getName(),name)){
                return value.getType();
            }
        }
        throw new CodecException("Codec name does not match a type.");
    }
}
