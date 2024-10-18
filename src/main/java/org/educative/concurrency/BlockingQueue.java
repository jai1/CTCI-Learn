package org.educative.concurrency;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BlockingQueue <T> {

    final int maxSize;
    final Queue<T> queue = new LinkedList<T>();

    final ReentrantLock lock = new ReentrantLock();
    final Condition dequeueCondition = lock.newCondition();
    final Condition enqueueCondition = lock.newCondition();

    public BlockingQueue(int maxSize) {
        this.maxSize = maxSize;
    }

    public T dequeue() throws InterruptedException {
        try {
            lock.lock();
            while (queue.isEmpty()) {
                dequeueCondition.await();
            }
            enqueueCondition.signal();
            return queue.remove();
        } finally {
            lock.unlock();
        }
    }

    public void enqueue(T e) throws InterruptedException {
        try {
            lock.lock();
            while (queue.size() == maxSize) {
                enqueueCondition.await();
            }
            queue.add(e);
            dequeueCondition.signal();
        } finally {
            lock.unlock();
        }
    }

    public int size() {
        try {
            lock.lock();
            return queue.size();
        } finally {
            lock.unlock();
        }
    }

    public static void main( String args[]) {
        int maxSize = 10;
        BlockingQueue<Integer> queue = new BlockingQueue<>(maxSize);
        ExecutorService executor = Executors.newFixedThreadPool(10);
        Random random = new Random();

        for (int i = 0; i < 5; i++) {
            final int startValue = i * 1000;
            executor.execute(() -> {
                for (int j = startValue; j < startValue + 1000; j++) {
                    try {
                        queue.enqueue(j);
                        // Simulate some work
                        Thread.sleep(random.nextInt(5));
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
        }

        // Dequeue threads (using lambdas)
        for (int i = 0; i < 5; i++) {
            executor.execute(() -> {
                for (int j = 0; j < 1000; j++) {
                    try {
                        Integer value = queue.dequeue();
                        if (value != null) {
                            System.out.println("Dequeued: " + value);
                        }
                        // Simulate some work
                        Thread.sleep(random.nextInt(5));
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
        }

        executor.shutdown();
    }

}
