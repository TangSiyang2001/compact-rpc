package com.tsy.rpc.message;

import com.tsy.rpc.annotation.RpcService;
import com.tsy.rpc.base.exception.AnnotationNotFoundException;
import com.tsy.rpc.base.message.Message;
import com.tsy.rpc.base.utils.SequenceIdUtils;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Steven.T
 * @date 2022/2/19
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class RpcRequest extends Message {

    /**
     * 调用接口的全限定名
     */
    private String interfaceName;

    /**
     * 调用方法名
     */
    private String methodName;

    /**
     * 返回值类型
     */
    private Class<?> returnType;

    /**
     * 方法参数类型
     */
    private Class<?>[] paramTypes;

    /**
     * 方法参数值
     */
    private Object[] paramValues;

    private String version;

    private String group;

    public RpcRequest(String interfaceName, String methodName, Class<?> returnType, Class<?>[] paramTypes,
                      Object[] paramValues, String version, String group) {

        super.setType(MessageType.RPC_REQUEST.getType());
        super.setSequenceId(SequenceIdUtils.nextId());
        this.interfaceName = interfaceName;
        this.methodName = methodName;
        this.returnType = returnType;
        this.paramTypes = paramTypes;
        this.paramValues = paramValues;
        this.version = version;
        this.group = group;
    }

    public String getServiceName() {
        final Class<? extends RpcRequest> clazz = this.getClass();
        final RpcService annotation = clazz.getAnnotation(RpcService.class);
        if (annotation == null) {
            throw new AnnotationNotFoundException("Missing Annotation @RpcService.");
        }
        final String name = annotation.name();
        if (StringUtils.isNotBlank(name)) {
            return name;
        }
        return interfaceName + group + version;
    }
}
