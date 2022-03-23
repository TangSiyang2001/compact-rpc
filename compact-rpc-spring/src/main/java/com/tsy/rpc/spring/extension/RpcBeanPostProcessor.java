package com.tsy.rpc.spring.extension;

import com.tsy.rpc.annotation.RpcService;
import com.tsy.rpc.annotation.RpcServiceImpl;
import com.tsy.rpc.base.extension.ExtensionLoader;
import com.tsy.rpc.base.factory.SingletonFactory;
import com.tsy.rpc.config.RpcServiceInfo;
import com.tsy.rpc.manager.ServiceBeansManager;
import com.tsy.rpc.manager.impl.DefaultServiceBeansManager;
import com.tsy.rpc.remote.client.RequestSender;
import com.tsy.rpc.remote.client.proxy.ClientProxy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * @author Steven.T
 * @date 2022/3/22
 */
@Slf4j
public class RpcBeanPostProcessor implements BeanPostProcessor {

    private final ServiceBeansManager serviceBeansManager;

    private final RequestSender requestSender;

    public RpcBeanPostProcessor() {
        this.serviceBeansManager = SingletonFactory.getInstance(DefaultServiceBeansManager.class);
        this.requestSender = ExtensionLoader.getExtensionLoader(RequestSender.class).getExtension("netty");
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass().isAnnotationPresent(RpcServiceImpl.class)) {
            final RpcServiceImpl annotation = bean.getClass().getAnnotation(RpcServiceImpl.class);
            //将注解中的服务信息及实体类进行服务注册
            final RpcServiceInfo serviceInfo = RpcServiceInfo
                    .builder()
                    .group(annotation.group())
                    .version(annotation.version())
                    .serviceName(annotation.name())
                    .serviceInstance(bean)
                    .build();
            serviceBeansManager.publishService(serviceInfo);
        }
        return bean;
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
