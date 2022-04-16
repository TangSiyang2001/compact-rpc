package com.tsy.prc.test.client;

import com.tsy.rpc.spring.annotation.EnableRpc;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Hello world!
 */
@EnableRpc
public class App {


    public static void main(String[] args) {
        try {
            AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(App.class);
            HelloController helloController = applicationContext.getBean(HelloController.class);
            System.out.println(helloController.test());
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
