package org.educative.concurrency;

import java.util.Random;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


class ExecutorCompletionServiceExample {

    static Random random = new Random(System.currentTimeMillis());

    public static void main( String args[] ) throws Exception {
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        ExecutorCompletionService<Integer> service =
                new ExecutorCompletionService<Integer>(threadPool);
        int count = 100;
        // Submit 10 trivial tasks.
        for (int i = 0; i < count; i++) {
            int finalI = i;
            service.submit(() -> {
                try {
                    // sleep for one second
                    Thread.sleep(random.nextInt(101));
                    System.out.println(finalI * finalI);
                } catch (InterruptedException ie) {
                    // swallow exception
                }
            }, i);
        }

        // wait for all tasks to get done
        for (int i = 0; i < count; i++) {
            Future<Integer> f = service.take();
            System.out.println("Thread" + f.get() + " got done.");
        }

        threadPool.shutdown();
    }

}