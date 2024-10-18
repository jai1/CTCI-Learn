package org.educative.concurrency;

import java.util.HashSet;
import java.util.Set;

public class TokenBucketFilter {
    final private int maxTokens;
    private int currTokens;
    volatile boolean stop = false;
    final private Thread tokenFiller = new Thread(() -> {
        while (! stop) {
            incrementToken();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // Do nothing
            }
        }
    });

    private synchronized void incrementToken() {
        if (currTokens < maxTokens) {
            currTokens++;
        }
        notify();
    }

    public TokenBucketFilter(int maxTokens) {
        this.maxTokens = maxTokens;
        this.tokenFiller.setDaemon(true);
        this.tokenFiller.start();
    }

    public void stop() throws InterruptedException {
        stop = true;
        this.tokenFiller.join();
    }

    public synchronized void getToken() throws InterruptedException {
        while(currTokens == 0) {
            wait();
        }
        currTokens--;
    }

    public static void main(String[] args) throws InterruptedException {
        Set<Thread> allThreads = new HashSet<Thread>();
        TokenBucketFilter tokenBucketFilter = new TokenBucketFilter(4);

        // accumulate 4 tokens
        Thread.sleep(5000);
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(() -> {
                try {
                    tokenBucketFilter.getToken();
                    System.out.println(Thread.currentThread().getName() + ": Got token");
                } catch (InterruptedException ie) {
                    // Ignore
                }
            });
            thread.setName("Thread_" + (i + 1));
            allThreads.add(thread);
        }

        for (Thread t : allThreads) {
            t.start();
        }

        for (Thread t : allThreads) {
            t.join();
        }

        tokenBucketFilter.stop();
    }
}
