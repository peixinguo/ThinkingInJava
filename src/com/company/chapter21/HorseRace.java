package com.company.chapter21;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

class Horse implements Runnable{
    private static int counter = 0;
    private final int id = counter++;
    private int strides = 0;
    private static Random random = new Random(47);
    private static CyclicBarrier barrier;

    public synchronized int getStrides() {
        return strides;
    }

    public Horse(CyclicBarrier cyclicBarrier) {
        this.barrier = cyclicBarrier;
    }

    @Override
    public void run() {
        try {
            while(!Thread.interrupted()){
                synchronized (this){
                    strides += random.nextInt(3); // produces 0,1,2
                }
                barrier.await();
            }
        } catch (InterruptedException e) {
            System.out.println(e);
        } catch (BrokenBarrierException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "Horse " + id + " ";
    }

    public String tracks(){
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < getStrides(); i++) {
            s.append("*");
        }
        s.append(id);
        return s.toString();
    }
}


public class HorseRace {

    static final int FINISH_LINE = 75;
    private List<Horse> horses = new ArrayList<>();
    private ExecutorService exec = Executors.newCachedThreadPool();
    private CyclicBarrier barrier;

    public HorseRace(int nHorses, final int pause) {
        barrier = new CyclicBarrier(nHorses, new Runnable() {
            @Override
            public void run() {
                StringBuilder s = new StringBuilder();
                for (int i = 0; i < FINISH_LINE; i++) {
                    s.append("=");// the fence on the racetrack
                }
                System.out.println(s);
                for (Horse horse: horses){
                    System.out.println(horse.tracks());
                }
                for (Horse horse: horses){
                    if (horse.getStrides() >= FINISH_LINE){
                        System.out.println(horse + "won!");
                        exec.shutdownNow();
                        return;
                    }
                    try{
                        TimeUnit.MILLISECONDS.sleep(pause);
                    }catch (InterruptedException e){
                        System.out.println("barrier-action sleep interrupted");
                    }
                }
            }
        });

        for (int i = 0; i < nHorses; i++) {
            Horse horse = new Horse(barrier);
            horses.add(horse);
            exec.execute(horse);
        }
    }

    public static void main(String[] args) {
        int nHorse = 7;
        int pause =200;
        new HorseRace(nHorse, pause);

    }
}
