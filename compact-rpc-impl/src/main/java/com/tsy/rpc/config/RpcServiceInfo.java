package com.tsy.rpc.config;

import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * 所调用的服务的相关信息
 *
 * @author Steven.T
 * @date 2022/2/22
 */
@Data
@Builder
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

    public String getServiceName() {
        final String delimiter = "-";
        final String postfix = delimiter + group + delimiter + version;
        if (StringUtils.isBlank(serviceName)) {
            return serviceInstance.getClass().getInterfaces()[0].getCanonicalName() + postfix;
        }
        return this.serviceName + postfix;
    }
}
