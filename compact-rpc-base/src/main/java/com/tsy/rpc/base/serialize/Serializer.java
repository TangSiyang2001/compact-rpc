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

    /**
     * 序列化
     *
     * @param t 序列化对象
     * @param <T> 序列化对象类型
     * @return 序列化结果
     */
    <T> byte[] serialize(T t);

    /**
     * 反序列化
     *
     * @param bytes 反序列化对象
     * @param clazz 反序列化class
     * @param <T> 反序列化类型
     * @return 反序列化结果
     */
    <T> T deserialize(byte[] bytes, Class<T> clazz);
}
