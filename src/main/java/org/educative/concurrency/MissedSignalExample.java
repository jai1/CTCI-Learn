package org.educative.concurrency;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.Semaphore;

public class MissedSignalExample {

    public static void main(String[] args) throws InterruptedException {
        incorrectUsageLock(); // Signal missed due to signal called before wait
        incorrectUsageSynchronized();
        correctUsage(); // semaphore fixes the issue by retaining the release info
    }

    public static void correctUsage() throws InterruptedException {

        final Semaphore semaphore = new Semaphore(1);

        Thread signaller = new Thread(() -> {
            semaphore.release();
            System.out.println("correctUsage: Sent signal");
        });

        Thread waiter = new Thread(() -> {
            try {
                if (semaphore.tryAcquire(2, TimeUnit.SECONDS)) {
                    System.out.println("correctUsage: Received signal");
                } else {
                    System.out.println("correctUsage: Didn't receive signal");
                }
            } catch (InterruptedException ie) {
                // handle interruption
            }
        });

        signaller.start();
        signaller.join();

        waiter.start();
        waiter.join();
    }

    volatile boolean signalReceived = false;

    public static void incorrectUsageSynchronized() throws InterruptedException {
        Object obj = new String();

        Thread signaller = new Thread(() -> {
            synchronized(obj) { // lock on object
                obj.notify();
                System.out.println("incorrectUsageSynchronized: Sent signal");
            }
        });

        Thread waiter = new Thread(() -> {
            synchronized(obj) { // lock on object
                try {
                    obj.wait();
                    System.out.println("incorrectUsageLock: Received signal");
                } catch (InterruptedException e) {
                    System.out.println("incorrectUsageLock: Interrupted.");
                }
            }
        });

        signaller.start();
        signaller.join();

        waiter.start();
        waiter.join(500);
        waiter.interrupt();
    }


        public static void incorrectUsageLock() throws InterruptedException {

        final ReentrantLock lock = new ReentrantLock();
        final Condition condition = lock.newCondition();

        Thread signaller = new Thread(() -> {
            lock.lock();
            condition.signal();
            System.out.println("incorrectUsageLock: Sent signal");
            lock.unlock();
        });

        Thread waiter = new Thread(() -> {
            lock.lock();
            try {
                if (condition.await(2, TimeUnit.SECONDS)) {
                    System.out.println("incorrectUsageLock: Received signal");
                } else {
                    System.out.println("incorrectUsageLock: Didn't receive signal");
                }
            } catch (InterruptedException ie) {
                // Ignore exception
            }
            lock.unlock();
        });

        signaller.start();
        signaller.join();

        waiter.start();
        waiter.join();
    }
}
