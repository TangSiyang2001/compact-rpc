package com.tsy.rpc;

import com.tsy.rpc.base.compress.Compressor;
import com.tsy.rpc.base.extension.ExtensionLoader;
import com.tsy.rpc.base.loadbalance.LoadBalancer;
import com.tsy.rpc.base.register.ServiceDiscovery;
import com.tsy.rpc.base.register.ServiceRegistry;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * @author Steven.T
 * @date 2022/3/20
 */
@Slf4j
public class ExtensionLoaderTest {

//    @Test
//    public void testSerializer(){
//        final Serializer serializer = ExtensionLoader.getExtensionLoader(Serializer.class).getExtension("protostuff");
//        assert serializer != null;
//        RpcRequest request = RpcRequest.builder()
//                .requestId(UUID.randomUUID().toString())
//                .version("1.0")
//                .interfaceName("test")
//                .build();
//        final byte[] serialize = serializer.serialize(request);
//        final RpcRequest res = serializer.deserialize(serialize, RpcRequest.class);
//        log.debug(res.toString());
//    }

    @Test
    public void testCompress(){
        final Compressor gzip = ExtensionLoader.getExtensionLoader(Compressor.class).getExtension("gzip");
        assert gzip != null;
        final byte[] compress = gzip.compress("123".getBytes(StandardCharsets.UTF_8));
        log.debug(Arrays.toString(gzip.decompress(compress)));
    }

    @Test
    public void testLoadBalancer(){
        final LoadBalancer loadbalance = ExtensionLoader.getExtensionLoader(LoadBalancer.class).getExtension("loadbalance");
        assert loadbalance != null;
        log.debug(loadbalance.toString());
    }

    @Test
    public void testServiceDiscovery(){
        final ServiceDiscovery serviceDiscovery = ExtensionLoader.getExtensionLoader(ServiceDiscovery.class).getExtension("nacos");
        assert serviceDiscovery != null;
        log.debug(serviceDiscovery.toString());
    }

    @Test
    public void testServiceRegistry(){
        final ServiceRegistry serviceRegistry = ExtensionLoader.getExtensionLoader(ServiceRegistry.class).getExtension("nacos");
        assert serviceRegistry != null;
        log.debug(serviceRegistry.toString());
    }
}
