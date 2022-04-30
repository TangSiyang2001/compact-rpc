package com.tsy.rpc.spring.annotation;

import com.tsy.rpc.spring.config.SpringRpcConfigManager;
import com.tsy.rpc.spring.extension.BeanDefinitionRegistrar;
import com.tsy.rpc.spring.extension.RpcClientBeanPostProcessor;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author Steven.T
 * @date 2022/4/23
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import({BeanDefinitionRegistrar.class, RpcClientBeanPostProcessor.class, SpringRpcConfigManager.class})
public @interface EnableRpcClient {
    String value() default "";
}
