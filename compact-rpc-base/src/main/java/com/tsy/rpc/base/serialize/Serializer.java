package com.tsy.rpc.base.serialize;

import com.tsy.rpc.base.extension.annotation.SPI;

/**
 * 序列化器
 *
 * @author Steven.T
 * @date 2022/2/18
 */
@SPI
public interface Serializer {

    byte[] serialize(Object obj);

    <T> T deserialize(byte[] bytes, Class<T> clazz);
}
