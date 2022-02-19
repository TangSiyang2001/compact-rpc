package com.tsy.rpc.remote.message;

import com.tsy.rpc.base.message.Message;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Steven.T
 * @date 2022/2/19
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RpcResponse extends Message {

    private Object value;

    private Exception exception;

}
