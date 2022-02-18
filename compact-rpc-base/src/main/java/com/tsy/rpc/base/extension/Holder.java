package com.tsy.rpc.base.extension;

/**
 * @author Steven.T
 * @date 2022/2/17
 */
public class Holder<T> {
    private volatile T instance;

    public T get(){
        return instance;
    }

    public void set(T t){
        instance = t;
    }
}
