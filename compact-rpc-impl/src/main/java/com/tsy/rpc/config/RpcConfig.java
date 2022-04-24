package com.tsy.rpc.config;

/**
 * @author Steven.T
 * @date 2022/2/20
 */
public class RpcConfig {

    private int serverPort;

    private String codec;

    private String compress;

    public static String getCodecAlgorithm(){
        //TODO:先写死，留待完善
        return "protostuff";
    }
}
