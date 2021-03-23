package com.company.chapter21;

import java.util.concurrent.TimeUnit;

class Daemon implements Runnable{
    private Thread[] threads = new Thread[10];

    @Override
    public void run() {
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new DaemonSpawn());
            threads[i].start();
            System.out.println("DaemonSpawn " + i + " started, ");
        }
        for (int i = 0; i < threads.length; i++) {
            System.out.println("t[" + i + "].isDaemon() = " + threads[i].isDaemon());
        }
        while(true){
            Thread.yield();
        }
    }
}

class DaemonSpawn implements Runnable{
    @Override
    public void run() {
        while(true){
            Thread.yield();
        }
    }
}


public class Daemons{
    public static void main(String[] args) throws Exception{
        Thread t = new Thread(new Daemon());
        t.setDaemon(true);
        t.start();
        System.out.println("t.isDaemon() = " + t.isDaemon());
        //Allow the daemon threads to finish their startup processes:
        TimeUnit.SECONDS.sleep(1);
    }
}
