package com.tsy.rpc.base.runtime;

/**
 * @author Steven.T
 * @date 2022/2/18
 */
public class RpcRuntime {

    /**
     * 获取cpu数目
     */
    public static int cpus() {
        return Runtime.getRuntime().availableProcessors();
    }

    /**
     * 系统关闭时(关闭jvm时)增加处理任务
     * @param task 相关任务
     */
    public static void addShutdownHook(Runnable task) {
        Runtime.getRuntime().addShutdownHook(new Thread(task));
    }

}
