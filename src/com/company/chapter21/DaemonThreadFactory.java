package com.company.chapter21;
import java.util.concurrent.ThreadFactory;

/**
 * 21.2.8 后台线程
 */
public class DaemonThreadFactory implements ThreadFactory {

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setDaemon(true);
        return thread;
    }
}
