package org.educative.concurrency;

import java.math.BigInteger;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockExample {
    private final ReentrantLock lock = new ReentrantLock();
    private final Random rand = new Random();
    public void run() {
        try {
            // Attempt to acquire the lock with a timeout of 1 second
            if (lock.tryLock(1, TimeUnit.NANOSECONDS)) {
                try {
                    BigInteger largeNumber = new BigInteger("12345678901234567890");
                    largeNumber.divide(new BigInteger(String.valueOf(rand.nextInt())));
                } finally {
                    lock.unlock();
                }
            } else {
                System.out.println(Thread.currentThread().getName() + " timed out waiting for the lock.");
            }
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + " interrupted.");
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) {
        ReentrantLockExample offer = new ReentrantLockExample();
        Thread[] threads = new Thread[1000];

        for (int i = 0; i<threads.length; i++) {
            threads[i] = new Thread(offer::run, "User " + i);
        }

        for (int i = 0; i<threads.length; i++) {
            threads[i].start();
        }
    }
}
