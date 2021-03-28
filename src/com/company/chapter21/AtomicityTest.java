package com.company.chapter21;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * return i 是原子性的，但是@field i 没有使用volatile修饰，return i仍然会获取到i的中间状态（奇数）
 */
public class AtomicityTest implements Runnable{
    private int i =0;
    public int getValue(){return i;}
    private synchronized void eventIncrement(){
        i++;
        i++;
    }

    @Override
    public void run() {
        while(true){
            eventIncrement();
        }
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        AtomicityTest atomicityTest = new AtomicityTest();
        executorService.execute(atomicityTest);
        while(true){
            int val = atomicityTest.getValue();
            if (val%2 != 0){
                System.out.println(val);
                System.exit(0);
            }
        }
    }
}
