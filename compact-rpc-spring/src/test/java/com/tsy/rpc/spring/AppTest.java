package com.tsy.rpc.spring;

import com.tsy.rpc.base.extension.ExtensionLoader;
import com.tsy.rpc.remote.client.RequestSender;
import com.tsy.rpc.remote.server.NettyRpcServer;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);
    }

    @Test
    public void testExtensionLoader() {
        Assert.assertNotNull(ExtensionLoader.getExtensionLoader(RequestSender.class).getExtension("netty"));
    }

    public static void main(String[] args) {
        final NettyRpcServer nettyRpcServer = new NettyRpcServer();
        nettyRpcServer.start();
        final RequestSender netty = ExtensionLoader.getExtensionLoader(RequestSender.class).getExtension("netty");
    }
}
