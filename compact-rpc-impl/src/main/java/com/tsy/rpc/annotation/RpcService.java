package com.tsy.rpc.annotation;

import java.lang.annotation.*;

/**
 * 表示为服务接口
 *
 * @author Steven.T
 * @date 2022/2/22
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RpcService {

    String name() default "";

    String group() default "";

    String version() default "";
}
