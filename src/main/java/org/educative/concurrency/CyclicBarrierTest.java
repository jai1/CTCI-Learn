package org.educative.concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierTest {

    static class CustomCyclicBarrier extends CyclicBarrier {
        int onboard = 0;
        int release = 0;
        private final int barrierCount;

        public CustomCyclicBarrier(int barrierCount) {
            super(barrierCount);
            this.barrierCount = barrierCount;
        }

        @Override
        public synchronized int await() throws InterruptedException {
            while (onboard == barrierCount) {
                wait();
            }

            onboard++;

            if (onboard == barrierCount) {
                notifyAll();
                release = onboard;
            }

            while (onboard < barrierCount) {
                wait();
            }

            release--;
            if (release == 0) {
                onboard = 0;
                notifyAll();
            }
            return onboard;
        }
    }

    // FOR TESTING
    private static volatile boolean stop = false;
    private static final int NUM_THREADS = 5;
    private static final int BARRIER_COUNT = 2;
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Testing custom Cyclic Barrier");
        cyclicBarrierTest(new CustomCyclicBarrier(BARRIER_COUNT));
        System.out.println("Testing inbuilt Cyclic Barrier");
        cyclicBarrierTest(new CyclicBarrier(BARRIER_COUNT));
    }



    public static void cyclicBarrierTest(CyclicBarrier barrier) throws InterruptedException {
        stop = false;
        List<Thread> threadList = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i<NUM_THREADS; i++) {
            Thread t = new Thread(() -> {
                int count = 0;
                while(! stop || count % BARRIER_COUNT != 0 ) {
                    count++;
                    try {
                        Thread.sleep(rand.nextInt(0, 10000));
                        System.out.println(Thread.currentThread().getName() + ": Waiting at " + System.currentTimeMillis()/1000);
                        barrier.await();
                        System.out.println(Thread.currentThread().getName() + ": Executed at " + System.currentTimeMillis()/1000);
                    } catch (InterruptedException | BrokenBarrierException e) {
                        // ignore
                    }
                }
            });
            t.setName("Thread " + i);
            t.start();
            threadList.add(t);
        }

        Thread.sleep(5000);
        stop = true;

        for (Thread t : threadList) {
            t.join();
        }
    }
}
