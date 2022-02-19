package com.tsy.rpc.base.factory;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Steven.T
 * @date 2022/2/19
 */
@Slf4j
public class SingletonFactory {

    private static final Map<Class<?>, Object> OBJECT_CACHE = new ConcurrentHashMap<>();

    private static final Object LOCK =new Object();

    private SingletonFactory() {
        throw new IllegalStateException("This is a factory class.");
    }

    public static <T> T getInstance(Class<T> clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("Class should be not null.");
        }
        Object instance = OBJECT_CACHE.get(clazz);
        if(instance == null){
            synchronized (LOCK){
                instance = OBJECT_CACHE.get(clazz);
                if(instance == null){
                    try {
                        instance = clazz.getDeclaredConstructor().newInstance();
                        OBJECT_CACHE.putIfAbsent(clazz,instance);
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                        log.error(e.getCause().getMessage());
                    }
                }
            }
        }
        return clazz.cast(instance);
    }
}
