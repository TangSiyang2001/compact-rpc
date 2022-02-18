package com.tsy.rpc.base.extension.annotation;

import java.lang.annotation.*;

/**
 * 用于标记spi扩展
 *
 * @author Steven.T
 * @date 2022/2/17
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SPI {
    String value() default "";
}
