package org.buzas.lesson3.question2;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Counter {
    private final int FINAL_COUNT = 10;
    private final int THREADS = 3;
    private final int ITERATIONS = 4;
    private volatile int count = 1;
    private final Lock lock = new ReentrantLock();

    public void start() {
        for (int i = 1; i <= THREADS; i++) {
            int name = i;
            new Thread(()-> {
                try {
                    shout(name);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    private synchronized void shout(int name) throws InterruptedException {
        if (count <= FINAL_COUNT){
            lock.lockInterruptibly();
        }
        try {
            for (int i = 0; i < ITERATIONS; i++) {
                if (count <= FINAL_COUNT) {
                    System.out.println("Thread" + name + " " + count);
                    count++;
                }
            }
        } finally {
            lock.unlock();
        }
    }
}
