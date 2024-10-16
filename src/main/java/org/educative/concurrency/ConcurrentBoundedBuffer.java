package org.educative.concurrency;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.TimeUnit;

public class ConcurrentBoundedBuffer<T> {
    private static final int NUM_MESSAGES = 1000;
    private final Queue<T> buffer;
    private final int capacity;
    private final Lock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();  // Condition for "not full"
    private final Condition notEmpty = lock.newCondition(); // Condition for "not empty"

    public ConcurrentBoundedBuffer(int capacity) {
        this.capacity = capacity;
        this.buffer = new LinkedList<>();
    }

    public void put(T item) throws InterruptedException {
        lock.lock();
        try {
            // Note: This while condition is necessary to circumvent spurious wakes.
            while (buffer.size() == capacity) {
                System.out.println(Thread.currentThread().getName() + " waiting on buffer to be emptied.");
                notFull.await(); // Wait if buffer is full
            }
            buffer.add(item);
            notEmpty.signal(); // Signal that the buffer is not empty
        } finally {
            lock.unlock();
        }
    }

    public T take() throws InterruptedException {
        lock.lock();
        try {
            while (buffer.size() == 0) {
                System.out.println(Thread.currentThread().getName() + " waiting on buffer to contain some items.");
                notEmpty.await(); // Wait if buffer is empty
            }
            notFull.signal(); // Signal that the buffer is not full
            return buffer.remove();
        } finally {
            lock.unlock();
        }
    }

    public int size() {
        lock.lock();
        try {
            return this.buffer.size();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        int capacity = 10;
        ConcurrentBoundedBuffer<Integer> buffer = new ConcurrentBoundedBuffer<>(capacity);
        ExecutorService executor = Executors.newFixedThreadPool(5);

        // Create producer threads
        executor.execute(() -> {
            Random random = new Random();
            for (int msg = 0; msg<NUM_MESSAGES; msg++) {
                try {
                    buffer.put(msg);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });


        // Create consumer threads
        executor.execute(() -> {
            Random random = new Random();
            for (int msg = 0; msg<NUM_MESSAGES; msg++) {
                try {
                    int item = buffer.take();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        // Shut down the executor
        executor.shutdown();
    }
}