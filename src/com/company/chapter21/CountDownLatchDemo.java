package com.company.chapter21;


import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class TaskPortion implements Runnable{
    private static int counter = 0;
    private final int id = counter++;
    private static Random random = new Random(47);
    private final CountDownLatch latch;

    public TaskPortion(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        try{
            doWork();
            latch.countDown();
        }catch (InterruptedException e){
            System.out.println(e);
        }
    }

    private void doWork()throws InterruptedException{
        TimeUnit.MILLISECONDS.sleep(random.nextInt(2000));
        System.out.println(this + " completed");
    }

    public String toString(){
        return String.format("%1$-3d ",id);
    }

}

class WaitTask implements Runnable{
    private static int counter = 0;
    private final int id = counter++;
    private static Random random = new Random(47);
    private final CountDownLatch latch;

    public WaitTask(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        try{
            latch.await();
            System.out.println("Latch barrier passed for " + this);
        }catch (InterruptedException e){
            System.out.println(this + " interrupted");
        }
    }

    @Override
    public String toString() {
        return String.format("waitingTask %1$-3d ",id);
    }
}
public class CountDownLatchDemo {

    static final int SIZE = 100;

    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        CountDownLatch latch = new CountDownLatch(SIZE);

        for (int i = 0; i < 10; i++) {
            exec.execute(new WaitTask(latch));
        }

        for (int i = 0; i < SIZE; i++) {
            exec.execute(new TaskPortion(latch));
        }
        System.out.println("Launched all task.");
        exec.shutdown();
    }
}
