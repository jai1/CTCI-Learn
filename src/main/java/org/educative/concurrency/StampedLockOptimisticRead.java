package org.educative.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;

public class StampedLockOptimisticRead {

    private static int counter = 0;
    private static final StampedLock lock = new StampedLock();

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(5);

        // Optimistic reader threads
        for (int i = 0; i < 3; i++) {
            executor.submit(() -> {
                long stamp = lock.tryOptimisticRead();
                int current = counter;
                // Simulate some work where the counter might be updated
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                if (!lock.validate(stamp)) { // Check if stamp is valid
                    stamp = lock.readLock(); // Fallback to read lock
                    try {
                        current = counter;
                    } finally {
                        lock.unlockRead(stamp);
                    }
                }
                System.out.println("Optimistic Reader " + Thread.currentThread().getId() + " - Counter: " + current);
            });
        }

        // Writer thread
        executor.submit(() -> {
            long stamp = lock.writeLock();
            try {
                counter++;
                System.out.println("Writer " + Thread.currentThread().getId() + " - Incremented counter: " + counter);
            } finally {
                lock.unlockWrite(stamp);
            }
        });

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);
    }
}