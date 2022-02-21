package com.tsy.rpc.serialize;

import com.tsy.rpc.base.exception.SerializationException;
import com.tsy.rpc.base.serialize.Serializer;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * https://zhuanlan.zhihu.com/p/78781763
 *
 * @author Steven.T
 * @date 2022/2/21
 */
public class ProtoStuffSerializer implements Serializer {

    /**
     * 固定的序列化buffer空间
     */
    private static final LinkedBuffer LINKED_BUFFER = LinkedBuffer.allocate();

    /**
     * 缓存schema（序列化对象的结构）
     */
    private static final Map<Class<?>, Schema<?>> SCHEMA_CACHE = new ConcurrentHashMap<>();

    @Override
    @SuppressWarnings("unchecked")
    public <T> byte[] serialize(T t) {
        final Class<T> clazz = (Class<T>) t.getClass();
        final Schema<T> schema = getSchema(clazz);
        try {
            return ProtostuffIOUtil.toByteArray(t,schema,LINKED_BUFFER);
        } finally {
            LINKED_BUFFER.clear();
        }
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        final Schema<T> schema = getSchema(clazz);
        if(schema == null){
            throw new SerializationException("Schema loading fail.");
        }
        final T t = schema.newMessage();
        ProtostuffIOUtil.mergeFrom(bytes,t,schema);
        return t;
    }

    @SuppressWarnings("unchecked")
    private <T> Schema<T> getSchema(Class<T> clazz) {
        Schema<?> schema = SCHEMA_CACHE.get(clazz);
        if (schema == null) {
            schema = RuntimeSchema.getSchema(clazz);
            if (schema != null) {
                SCHEMA_CACHE.put(clazz, schema);
            }
        }
        return (Schema<T>) schema;
    }
}
