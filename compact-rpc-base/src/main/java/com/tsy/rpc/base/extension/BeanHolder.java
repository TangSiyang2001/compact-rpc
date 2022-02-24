package com.tsy.rpc.base.extension;

import com.tsy.rpc.base.bean.Holder;

/**
 * @author Steven.T
 * @date 2022/2/17
 */
public class BeanHolder<T> implements Holder<T> {
    private volatile T instance;

    @Override
    public T get(){
        return instance;
    }

    @Override
    public void set(T t){
        instance = t;
    }
}
