package com.tsy.rpc.spring.annotation;

import java.lang.annotation.*;

/**
 * @author Steven.T
 * @date 2022/3/22
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RpcScan {
    String[] basePackages();
}
