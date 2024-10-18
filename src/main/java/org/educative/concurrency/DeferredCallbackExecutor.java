package org.educative.concurrency;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;

public class DeferredCallbackExecutor {

    class CallBack {
        long executeAt;
        String message;

        public CallBack(long executeAfterInMillis, String message) {
            this.executeAt = System.currentTimeMillis() + executeAfterInMillis;
            this.message = message;
        }
    }

    volatile boolean stop = false;
    final PriorityQueue<CallBack> callBacks;
    Thread t1 = new Thread(() -> {
        while (! stop) {
            long currTimeInMillis = System.currentTimeMillis();
            try {
                CallBack cb = getCallBack(currTimeInMillis);
                if (cb != null) {
                    System.out.println("Calling " + cb.message);
                }
            } catch (InterruptedException e) {
                // do nothing
            }
        }
    });

    public DeferredCallbackExecutor() {
        callBacks = new PriorityQueue<>(Comparator.comparingLong(a -> a.executeAt));
        t1.setDaemon(true);
        t1.start();
    }

    public void stop() throws InterruptedException {
        stop = true;
        synchronized (this) {
            notify();
        }
        t1.join();
    }


    public synchronized void registerCallBack(CallBack cb) {
        callBacks.add(cb);
        notify();
    }

    private synchronized CallBack getCallBack(long currTimeInMillis) throws InterruptedException {
        while (callBacks.size() == 0) {
            wait();
            if (stop) {
                return null;
            }
            currTimeInMillis = System.currentTimeMillis();
        }
        CallBack cb = callBacks.peek();
        if (cb.executeAt > currTimeInMillis) {
            wait(cb.executeAt - currTimeInMillis);
            return null;
        } else {
            return callBacks.remove();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        DeferredCallbackExecutor executor = new DeferredCallbackExecutor();
        Random random = new Random();
        for (int j = 0; j<5; j++) {
            for (int i = 0; i < 100; i++) {
                CallBack cb = executor.new CallBack(random.nextLong(0, 1000), "Callback " + i);
                executor.registerCallBack(cb);
            }
            Thread.sleep(1000);
        }
        executor.stop();
    }
}
