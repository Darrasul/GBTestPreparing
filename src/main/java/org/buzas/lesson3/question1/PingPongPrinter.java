package org.buzas.lesson3.question1;

public class PingPongPrinter {
    private final Object monitor = new Object();
    private volatile boolean isPing = true;
//    Число не из задания, но ввел, чтобы на while(true) не крутить вечно запись
    private final int FINAL_COUNT = 10;
    private volatile int currentCount = 1;

    public void printPing() {
        synchronized (monitor) {
            try {
                while (currentCount != FINAL_COUNT) {
                    while (!isPing){
                        monitor.wait();
                    }
                    System.out.println("ping");
                    currentCount++;
                    isPing = false;
                    monitor.notify();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void printPong() {
        synchronized (monitor) {
            try {
                while (currentCount != FINAL_COUNT) {
                    while (isPing){
                        monitor.wait();
                    }
                    System.out.println("pong");
                    currentCount++;
                    isPing = true;
                    monitor.notify();
                }
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
