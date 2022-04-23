package com.tsy.rpc.test.server;

import com.tsy.rpc.spring.annotation.EnableRpcServer;
import com.tsy.rpc.spring.annotation.RpcScan;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Hello world!
 */
@EnableRpcServer
@RpcScan(basePackage = "com.tsy.rpc.test")
public class App {
    public static void main(String[] args) {
        //直接创建一个容器，由于标注了@EnableRpcServer，将会自动启动Rpc服务器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(App.class);
    }
}
