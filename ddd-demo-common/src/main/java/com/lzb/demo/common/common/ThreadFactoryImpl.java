package com.lzb.demo.common.common;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 * @author lzb
 * @date 2022/6/27
 */
@Slf4j
public class ThreadFactoryImpl implements ThreadFactory {

    private final AtomicLong threadIndex = new AtomicLong(0);
    private final String threadNamePrefix;
    private final boolean daemon;

    public ThreadFactoryImpl(final String threadNamePrefix) {
        this(threadNamePrefix, false);
    }

    public ThreadFactoryImpl(final String threadNamePrefix, boolean daemon) {
        this.threadNamePrefix = threadNamePrefix;
        this.daemon = daemon;
    }

    @Override
    public Thread newThread(@NonNull Runnable r) {
        Thread thread = new Thread(r, threadNamePrefix + this.threadIndex.incrementAndGet());
        thread.setDaemon(daemon);
        thread.setUncaughtExceptionHandler((t, e) -> {
            log.error("线程池执行异常: ", e);
        });
        return thread;
    }
}
