package com.tsy.rpc.base.extension;

import com.tsy.rpc.base.exception.NoSuchExtensionException;
import com.tsy.rpc.base.extension.annotation.SPI;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 模仿dubbo ExtensionLoader实现 根据接口及相关信息载入实现类
 *
 * @author Steven.T
 * @date 2022/2/17
 */
@Slf4j
public class ExtensionLoader<T> {

    private static final String META_INF_PATH = "META-INF/extensions/";

    /**
     * ExtensionLoader共用的静态缓存
     */
    private static final Map<Class<?>, ExtensionLoader<?>> EXTENSION_LOADERS_CACHE = new ConcurrentHashMap<>(64);

    /**
     * 所有实现类共用的静态缓存
     */
    private static final Map<Class<?>, Object> EXTENSION_INSTANCES_CACHE = new ConcurrentHashMap<>(64);

    /**
     * 当前加载器所加载的接口
     */
    private final Class<?> targetType;

    /**
     * 当前加载器的实现类对象实例缓存
     */
    private final Map<String, BeanHolder<Object>> cachedInstances = new ConcurrentHashMap<>(16);

    /**
     * 关联扩展名和类型
     * 在META-INF下，例如loadBalance=com.tsy.rpc.loadbalance.loadbalancer.ConsistentHashLoadBalance
     * loadBalance即扩展名
     */
    private final BeanHolder<Map<String, Class<?>>> cachedClassesHolder = new BeanHolder<>();

    private ExtensionLoader(Class<?> clazz) {
        this.targetType = clazz;
    }

    /**
     * 工厂方法
     *
     * @param clazz 目标类型
     * @param <E>   目标接口
     * @return ExtensionLoader
     */
    @SuppressWarnings("unchecked")
    public static <E> ExtensionLoader<E> getExtensionLoader(Class<E> clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("Cannot load clazz of null.");
        }
        if (!clazz.isInterface()) {
            throw new IllegalArgumentException("Loaded target should be an interface.");
        }
        if (!clazz.isAnnotationPresent(SPI.class)) {
            throw new IllegalArgumentException("Loaded target should be annotated with @SPI.");
        }
        ExtensionLoader<?> extensionLoader = EXTENSION_LOADERS_CACHE.get(clazz);
        if (extensionLoader == null) {
            //说明未缓存
            EXTENSION_LOADERS_CACHE.putIfAbsent(clazz, new ExtensionLoader<>(clazz));
            extensionLoader = EXTENSION_LOADERS_CACHE.get(clazz);
        }
        return (ExtensionLoader<E>) extensionLoader;
    }

    /**
     * 获取扩展实现类
     *
     * @param name 扩展名
     * @return 实现类
     */
    @SuppressWarnings("unchecked")
    public T getExtension(String name) {
        if (name == null || name.trim().length() == 0) {
            throw new IllegalArgumentException("Extension name should not be blank.");
        }
        BeanHolder<Object> holder = cachedInstances.get(name);
        if (holder == null) {
            //创建相应缓存
            cachedInstances.putIfAbsent(name, new BeanHolder<>());
            holder = cachedInstances.get(name);
        }
        Object instance = holder.get();
        if (instance == null) {
            synchronized (this) {
                instance = holder.get();
                if (instance == null) {
                    instance = createExtension(name);
                    holder.set(instance);
                }
            }
        }
        return (T) instance;
    }

    @SuppressWarnings("unchecked")
    private T createExtension(String name) {
        final Class<?> clazz = getExtensionClasses().get(name);
        if (clazz == null) {
            throw new NoSuchExtensionException("Extension" + name + "does not exists.");
        }
        Object instance = EXTENSION_INSTANCES_CACHE.get(clazz);
        if (instance == null) {
            //未缓存
            try {
                EXTENSION_INSTANCES_CACHE.putIfAbsent(clazz, clazz.getConstructor().newInstance());
                instance = EXTENSION_INSTANCES_CACHE.get(clazz);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                log.error(e.getCause().getMessage());
            }
        }
        return (T) instance;
    }

    private Map<String, Class<?>> getExtensionClasses() {
        Map<String, Class<?>> classMap = cachedClassesHolder.get();
        if (classMap == null) {
            synchronized (this) {
                classMap = cachedClassesHolder.get();
                if (classMap == null) {
                    classMap = new HashMap<>(16);
                    loadDirectory(classMap);
                    cachedClassesHolder.set(classMap);
                }
            }
        }
        return classMap;
    }

    /**
     * 使用参数传值
     *
     * @param classMap 扩展名-类型映射
     */
    private void loadDirectory(Map<String, Class<?>> classMap) {
        final String filename = META_INF_PATH + targetType.getName();
        final ClassLoader classLoader = ExtensionLoader.class.getClassLoader();
        try {
            //需要用getResources获取所有父依赖中的资源
            final Enumeration<URL> urlEnumeration = classLoader.getResources(filename);
            if (urlEnumeration != null) {
                while (urlEnumeration.hasMoreElements()) {
                    final URL url = urlEnumeration.nextElement();
                    loadResource(classMap, classLoader, url);
                }
            }
        } catch (IOException e) {
            log.error(e.getCause().getMessage());
        }
    }

    private void loadResource(Map<String, Class<?>> classMap, ClassLoader classLoader, URL url) {
        try (final BufferedReader bufferedReader =
                     new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8.name()))) {

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                //处理# 开头的注释
                final int ci = line.indexOf("#");
                if (ci != -1) {
                    //说明该行存在注释
                    line = line.substring(0, ci);
                }
                line = line.trim();
                if (line.length() > 0) {
                    final int ei = line.indexOf("=");
                    final String extName = line.substring(0, ei).trim();
                    final String className = line.substring(ei + 1).trim();
                    if (extName.length() > 0 && className.length() > 0) {
                        final Class<?> clazz = classLoader.loadClass(className);
                        classMap.put(extName, clazz);
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            log.error(e.getCause().getMessage());
        }
    }
}
