package com.tsy.rpc.base.utils;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Steven.T
 * @date 2022/2/19
 */
public class SequenceIdUtils {

    private SequenceIdUtils(){
        throw new IllegalStateException("This is a util class.");
    }

    private static final AtomicInteger ID = new AtomicInteger();

    public static int nextId(){
        return ID.getAndIncrement();
    }

}
