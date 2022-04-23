package com.tsy.rpc.spring.extension;

import com.tsy.rpc.remote.server.NettyRpcServer;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * @author Steven.T
 * @date 2022/4/23
 */
public class ContextListener implements ApplicationListener<ContextRefreshedEvent>, ApplicationContextAware {

    private ApplicationContext applicationContext;

    /**
     * 在容器启动完毕启动nettyRpcServer
     * @param event 容器加载完毕事件
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        NettyRpcServer nettyRpcServer = applicationContext.getBean(NettyRpcServer.class);
        nettyRpcServer.start();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
