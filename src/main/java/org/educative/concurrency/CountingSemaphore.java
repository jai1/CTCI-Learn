package org.educative.concurrency;

public class CountingSemaphore {

    int availablePermits = 0;
    int maxPermits = 0;

    public CountingSemaphore(int initialPermits) {
        throw new IllegalArgumentException("Use normal semaphore instead.");
    }

    public CountingSemaphore(int initialPermits, int maxPermits) {
        this.availablePermits = initialPermits;
        this.maxPermits = maxPermits;
    }

    public synchronized void acquire() throws InterruptedException {
        while (availablePermits == 0) {
            wait();
        }
        availablePermits--;
        notify();
    }

    public synchronized void release() throws InterruptedException {
        while (availablePermits == maxPermits) {
            wait();
        }
        availablePermits++;
        notify();
    }

    public static void main( String args[] ) throws InterruptedException {
        final CountingSemaphore cs = new CountingSemaphore(3, 40);

        Thread t1 = new Thread(() -> {
            try {
                for (int i = 0; i < 500; i++) {
                    cs.acquire();
                    System.out.println("Ping " + i);
                }
            } catch (InterruptedException ie) {
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 500; i++) {
                try {
                    cs.release();
                    System.out.println("Pong " + i);
                } catch (InterruptedException ie) {
                }
            }
        });

        t2.start();
        t1.start();
        t1.join();
        t2.join();
    }

}
