package com.tsy.rpc.spring.extension;

import com.tsy.rpc.annotation.RpcServiceImpl;
import com.tsy.rpc.base.factory.SingletonFactory;
import com.tsy.rpc.config.RpcServiceInfo;
import com.tsy.rpc.manager.ServiceBeansManager;
import com.tsy.rpc.manager.impl.DefaultServiceBeansManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * @author Steven.T
 * @date 2022/3/22
 */
@Slf4j
public class RpcServerBeanPostProcessor implements BeanPostProcessor {

    private final ServiceBeansManager serviceBeansManager;

    public RpcServerBeanPostProcessor() {
        this.serviceBeansManager = SingletonFactory.getInstance(DefaultServiceBeansManager.class);
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

}
