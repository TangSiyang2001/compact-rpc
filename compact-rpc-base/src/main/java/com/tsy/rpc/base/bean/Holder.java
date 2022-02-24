package com.tsy.rpc.base.bean;

/**
 * @author Steven.T
 * @date 2022/2/22
 */
public interface Holder<T> {
    /**
     * 获取实例
     * @return 实例
     */
    T get();

    /**
     * 设置实例
     * @param t 实例
     */
    void set(T t);
}
