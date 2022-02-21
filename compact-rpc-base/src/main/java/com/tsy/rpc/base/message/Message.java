package com.tsy.rpc.base.message;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Steven.T
 * @date 2022/2/19
 */
@Data
public abstract class Message implements Serializable {

    private static final long serialVersionUID = 42L;

    private int sequenceId;

    private byte type;
}
