package org.educative.concurrency;

public class Main {
    public static void main(String[] args) throws Exception {
        BlockingQueue.main(args);
        ConcurrentBoundedBuffer.main(args);
        ConditionWaitNotifyExample.main(args);
        CountingSemaphore.main(args);
        CyclicBarrierTest.main(args);
        DaemonThreadExample.main(args);
        DeferredCallbackExecutor.main(args);
        ExecutorCompletionServiceExample.main(args);
        MissedSignalExample.main(args);
        MultiThreadedQuickSort.main(args);
        NonBlockingStack.main(args);
        PrintNumberSeries.main(args);
        ReadWriteLock.main(args);
        ReentrantLockExample.main(args);
        StampedLockOptimisticRead.main(args);
        TokenBucketFilter.main(args);
    }
}