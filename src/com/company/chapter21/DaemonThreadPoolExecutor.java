package com.company.chapter21;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 自定义线程池
 */
public class DaemonThreadPoolExecutor extends ThreadPoolExecutor {

    public DaemonThreadPoolExecutor() {
        super(0, Integer.MAX_VALUE,60L, TimeUnit.SECONDS, new SynchronousQueue<>(), new DaemonThreadFactory());
    }
}
