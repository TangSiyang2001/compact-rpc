package com.tsy.rpc.base.thread;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * TODO:添加默认值
 * @author Steven.T
 * @date 2022/2/24
 */
@Data
@Builder
@NoArgsConstructor
public class ThreadPoolConfig {

    /**
     * 核心线程数
     */
    private int corePoolSize;

    /**
     * 最大线程数
     */
    private int maximumPoolSize;

    /**
     * 救急线程（maximumPoolSize - corePoolSize）空闲时存活时间
     */
    private long keepAliveTime;

    /**
     * 时间单位
     */
    private TimeUnit timeUnit;

    /**
     * 任务队列
     * 有界阻塞队列 ArrayBlockingQueue
     * 无界阻塞队列 LinkedBlockingQueue
     * 最多只有一个同步元素的 SynchronousQueue
     * 优先队列 PriorityBlockingQueue
     */
    private BlockingQueue<Runnable> workQueue;

    /**
     * 给线程赋名的线程工厂
     */
    private ThreadFactory threadFactory;

    /**
     * 拒绝策略
     */
    private RejectedExecutionHandler rejectedPolicy;

}
