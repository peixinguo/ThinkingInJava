package com.company.chapter21;

import java.util.concurrent.TimeUnit;

/**
 * 后台线程不会执行finally子句
 */
class ADaemon implements Runnable{
    @Override
    public void run() {
        try {
            System.out.println("Starting ADaemon");
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            System.out.println("Exiting via InterruptedException");
        }finally {
            System.out.println("This should always run?");
        }
    }
}

public class DaemonsDontRunFinally {
    public static void main(String[] args) {
        Thread thread = new Thread(new ADaemon());
        //注释掉setDaemon(true)则会执行finally子句
        thread.setDaemon(true);
        thread.start();
    }
}
