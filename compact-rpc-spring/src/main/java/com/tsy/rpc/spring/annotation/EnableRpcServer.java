package com.tsy.rpc.spring.annotation;

import com.tsy.rpc.remote.server.NettyRpcServer;
import com.tsy.rpc.spring.extension.BeanDefinitionRegistrar;
import com.tsy.rpc.spring.extension.ContextListener;
import com.tsy.rpc.spring.extension.RpcServerBeanPostProcessor;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 开启Rpc功能，使用{@code @Import}注入自定义注册器、服务端实现和客户端Bean后置处理器
 *
 * @author Steven.T
 * @date 2022/3/22
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import({BeanDefinitionRegistrar.class, NettyRpcServer.class, RpcServerBeanPostProcessor.class, ContextListener.class})
public @interface EnableRpcServer {
    String value() default "";
}
