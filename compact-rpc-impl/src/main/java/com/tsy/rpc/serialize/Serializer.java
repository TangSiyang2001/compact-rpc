package com.tsy.rpc.serialize;

/**
 * 序列化器
 *
 * @author Steven.T
 * @date 2022/2/18
 */
public interface Serializer {

    byte[] serialize(Object obj);

    <T> T deserialize(byte[] bytes, Class<T> clazz);
}
