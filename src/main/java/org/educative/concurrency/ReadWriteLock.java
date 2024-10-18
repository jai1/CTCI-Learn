package org.educative.concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class ReadWriteLock {
    ReentrantLock lock = new ReentrantLock();
    Condition lockRequestedCondition = lock.newCondition();
    Condition lockAcquiredCondition = lock.newCondition();
    boolean writeLockRequested = false;
    boolean writeLockAcquired = false;
    ThreadLocal<Boolean> readLockAcquired = ThreadLocal.withInitial(() -> false);

    public void acquireReadLock() throws InterruptedException {
        try {
            lock.lock();
            if (readLockAcquired.get()) {
                return;
            }
            while (writeLockRequested) {
                lockRequestedCondition.await();
            }
            readLockAcquired.set(true);
        } finally {
            lock.unlock();
        }
    }

    public void releaseReadLock() {
        try {
            lock.lock();
            if (! readLockAcquired.get()) {
                return;
            }
            readLockAcquired.set(false);
            lockAcquiredCondition.signal();
        } finally {
            lock.unlock();
        }
    }

    public void acquireWriteLock() throws InterruptedException {
        try {
            lock.lock();
            writeLockRequested = true;
            while (writeLockAcquired || readLockAcquired.get()) {
                lockAcquiredCondition.await();
            }
            writeLockAcquired = true;
            writeLockRequested = false;
            // NOTE: Here signal all needs to be used since multiple reader threads are waiting at the same time.
            lockRequestedCondition.signalAll();
            lockAcquiredCondition.signal();
        } finally {
            lock.unlock();
        }
    }

    public void releaseWriteLock() {
        try {
            lock.lock();
            writeLockAcquired = false;
            lockAcquiredCondition.signal();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String args[]) throws InterruptedException {

        final ReadWriteLock rwl = new ReadWriteLock();
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i<3; i++) {
            Thread reader = new Thread(() -> {
                try {
                    for (int j = 0; j < 10; j++) {
                        System.out.println(Thread.currentThread().getName() + ": Acquiring readlock");
                        rwl.acquireReadLock();
                        rwl.acquireReadLock();
                        System.out.println(Thread.currentThread().getName() + ": Readlock acquired");
                        Thread.sleep(100);
                        rwl.releaseReadLock();
                        System.out.println(Thread.currentThread().getName() + ": Readlock released");
                    }
                } catch (InterruptedException ie) {

                }
            });
            reader.setName("Reader " + i);
            reader.start();
            threads.add(reader);
        }

        Thread.sleep(100);
        for (int i = 0; i<3; i++) {
            Thread writer = new Thread(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + ": Attempting to acquire write lock.");
                    rwl.acquireWriteLock();
                    System.out.println(Thread.currentThread().getName() + ": Write lock acquired.");
                    Thread.sleep(500);
                    rwl.releaseWriteLock();
                    System.out.println(Thread.currentThread().getName() + ": Write lock released.");
                } catch (InterruptedException ie) {

                }
            });
            writer.setName("Writer " + i);
            writer.start();
            threads.add(writer);
        }

        for(Thread t : threads) {
            t.join();
        }


    }
}