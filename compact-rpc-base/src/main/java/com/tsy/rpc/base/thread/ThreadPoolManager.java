package com.tsy.rpc.base.thread;

import com.tsy.rpc.base.exception.ThreadManageException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;

/**
 * 创建线程池的工厂对象
 *
 * @author Steven.T
 * @date 2022/2/23
 */
@Slf4j
public class ThreadPoolManager {

    private static final Map<String, ThreadPoolManager> THREAD_POOL_MANAGER_CACHE = new ConcurrentHashMap<>(32);

    /**
     * 不同的线程名前缀代表不同种类的业务
     */
    private final String threadNamePrefix;

    /**
     * 线程池配置
     */
    private ThreadPoolConfig threadPoolConfig;

    private ExecutorService executorService;

    private ThreadPoolManager(String threadNamePrefix) {
        this.threadNamePrefix = threadNamePrefix;
    }

    public static ThreadPoolManager getManager(String namePrefix) {
        return THREAD_POOL_MANAGER_CACHE.computeIfAbsent(namePrefix, ThreadPoolManager::createThreadPoolManager);
    }

    public static void shutDownAllTreadPool() {
        final Set<Map.Entry<String, ThreadPoolManager>> entries = THREAD_POOL_MANAGER_CACHE
                .entrySet();
        entries.parallelStream()
                .forEach(entry -> {
                    final ThreadPoolManager manager = entry.getValue();
                    final String name = entry.getKey();
                    final ExecutorService executorService = manager.getExecutorService();
                    doShutDown(name, executorService);
                });
        entries.clear();
    }

    private static void doShutDown(String name, ExecutorService pool) {
        if (pool == null) {
            throw new ThreadManageException("Shut down with manager " + name + " without a pool.");
        }
        try {
            pool.shutdown();
            final boolean isShutdown = pool.awaitTermination(10, TimeUnit.SECONDS);
            if (isShutdown) {
                log.info("Shut down thread pool [{}] [{}]", name, pool.isTerminated());
            } else {
                throw new ThreadManageException("Shut down " + name + " failed,state:");
            }
        } catch (InterruptedException e) {
            throw new ThreadManageException("Shut down " + name + " failed,interrupted:", e.getCause());
        }
    }

    private static ThreadPoolManager createThreadPoolManager(String threadNamePrefix) {
        return new ThreadPoolManager(threadNamePrefix);
    }

    public ExecutorService createThreadPool() {
        //TDOD:不能写死
        return createThreadPool(new ThreadPoolConfig());
    }

    public ExecutorService createThreadPool(ThreadPoolConfig config) {
        return createThreadPool(config, false);
    }

    public ExecutorService createThreadPool(ThreadPoolConfig config, boolean daemon) {
        this.threadPoolConfig = config;
        this.executorService = new ThreadPoolExecutor(config.getCorePoolSize(), config.getMaximumPoolSize(),
                config.getKeepAliveTime(), config.getTimeUnit(), config.getWorkQueue(),
                config.getThreadFactory() == null ? createDefaultThreadFactory(daemon) : config.getThreadFactory(),
                config.getRejectedPolicy() == null ? new ThreadPoolExecutor.AbortPolicy() : config.getRejectedPolicy());
        return this.executorService;
    }

    /**
     * 使用工具类创建ThreadFactory
     * https://commons.apache.org/proper/commons-lang/apidocs/org/apache/commons/lang3/concurrent/BasicThreadFactory.html
     *
     * @param daemon 是否为守护线程
     * @return ThreadFactory
     */
    private ThreadFactory createDefaultThreadFactory(boolean daemon) {
        return new BasicThreadFactory
                .Builder()
                .namingPattern(this.threadNamePrefix + "-%d")
                .daemon(daemon)
                .build();
    }

    public ExecutorService getExecutorService() {
        return this.executorService;
    }

    public void shutDownPool() {
        doShutDown(this.threadNamePrefix, this.executorService);
        THREAD_POOL_MANAGER_CACHE.remove(this.threadNamePrefix);
    }
}
