package com.tsy.prc.test.client;

import com.tsy.rpc.annotation.RpcService;
import com.tsy.rpc.test.IHelloService;
import org.springframework.stereotype.Component;

/**
 * @author Steven.T
 * @date 2022/4/13
 */
@Component
public class HelloController {


    @RpcService(version = "version1", group = "test1",name = "HelloService")
    private IHelloService helloService;

    public String test(){
        return helloService.echo("hello rpc!");
    }

}
