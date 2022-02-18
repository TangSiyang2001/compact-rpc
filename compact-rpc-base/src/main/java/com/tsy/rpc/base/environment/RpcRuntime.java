package com.tsy.rpc.base.environment;

/**
 * @author Steven.T
 * @date 2022/2/18
 */
public class RpcRuntime {
    public static int cpus(){
        return Runtime.getRuntime().availableProcessors();
    }
}
