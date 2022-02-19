package com.tsy.rpc.base.utils;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Steven.T
 * @date 2022/2/19
 */
public class SequenceIdUtils {

    private SequenceIdUtils(){
        throw new IllegalStateException("This is a util class.");
    }

    public static final AtomicLong ID = new AtomicLong();

    public static long nextId(){
        return ID.getAndIncrement();
    }
}
