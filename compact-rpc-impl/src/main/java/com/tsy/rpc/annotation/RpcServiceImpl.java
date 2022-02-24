package com.tsy.rpc.annotation;

import java.lang.annotation.*;

/**
 * 表示服务实现
 *
 * @author Steven.T
 * @date 2022/2/22
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RpcServiceImpl {
    //TODO:设计内容
}
