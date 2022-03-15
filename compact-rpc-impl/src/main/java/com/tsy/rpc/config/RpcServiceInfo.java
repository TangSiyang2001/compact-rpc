package com.tsy.rpc.config;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 所调用的服务的相关信息
 *
 * @author Steven.T
 * @date 2022/2/22
 */
@Data
@AllArgsConstructor
public class RpcServiceInfo {

    /**
     * 当接口有多实现时，应用group区分
     */
    private String group;

    private String version;

    /**
     * TODO:考虑是否可自定义，或者直接读取接口名作为（如果直接取接口名则无需该域，直接写一返回对应接口名的get方法）
     */
    private String serviceName;

    private Object serviceInstance;

}
