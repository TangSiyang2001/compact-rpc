package com.tsy.rpc.test.server;

import com.tsy.rpc.remote.server.NettyRpcServer;
import com.tsy.rpc.spring.annotation.EnableRpc;
import com.tsy.rpc.spring.annotation.RpcScan;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Hello world!
 */
@EnableRpc
@RpcScan(basePackage = "com.tsy.rpc.test")
public class App {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(App.class);
        NettyRpcServer nettyRpcServer = applicationContext.getBean(NettyRpcServer.class);
        nettyRpcServer.start();
    }
}
