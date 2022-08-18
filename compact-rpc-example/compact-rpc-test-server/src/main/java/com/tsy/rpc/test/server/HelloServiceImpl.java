package com.tsy.rpc.test.server;

import com.tsy.rpc.annotation.RpcServiceImpl;
import com.tsy.rpc.test.IHelloService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Steven.T
 * @date 2022/4/13
 */
@Slf4j
@RpcServiceImpl(group = "test1", version = "version1",name = "HelloService")
public class HelloServiceImpl implements IHelloService {

    @Override
    public void hello() {
        log.info("hello world!");
    }

    @Override
    public String echo(String content) {
        return content;
    }
}
