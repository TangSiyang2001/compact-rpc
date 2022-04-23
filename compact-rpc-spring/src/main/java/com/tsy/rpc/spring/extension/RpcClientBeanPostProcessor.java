package com.tsy.rpc.spring.extension;

import com.tsy.rpc.annotation.RpcService;
import com.tsy.rpc.base.extension.ExtensionLoader;
import com.tsy.rpc.config.RpcServiceInfo;
import com.tsy.rpc.remote.client.RequestSender;
import com.tsy.rpc.remote.client.proxy.ClientProxy;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * @author Steven.T
 * @date 2022/4/23
 */
public class RpcClientBeanPostProcessor implements BeanPostProcessor {

    private final RequestSender requestSender;

    public RpcClientBeanPostProcessor() {
        this.requestSender = ExtensionLoader.getExtensionLoader(RequestSender.class).getExtension("netty");
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        //排查有@RpcService注解的字段，将其赋值为代理类
        final Field[] fields = bean.getClass().getDeclaredFields();
        Arrays.stream(fields)
                .filter(field -> field.isAnnotationPresent(RpcService.class))
                .forEach(field -> {
                    final RpcService annotation = field.getAnnotation(RpcService.class);
                    final RpcServiceInfo serviceInfo = RpcServiceInfo
                            .builder()
                            .serviceName(annotation.name())
                            .group(annotation.group())
                            .version(annotation.version())
                            .build();
                    final ClientProxy clientProxy = new ClientProxy(requestSender, serviceInfo);
                    final Object proxy = clientProxy.getProxy(field.getType());
                    ReflectionUtils.makeAccessible(field);
                    ReflectionUtils.setField(field, bean, proxy);
                });
        return bean;
    }
}

