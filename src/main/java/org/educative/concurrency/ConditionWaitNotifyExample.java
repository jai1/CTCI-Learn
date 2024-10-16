package org.educative.concurrency;

public class ConditionWaitNotifyExample {
    private boolean conditionMet = false;

    public synchronized void waitForCondition() throws InterruptedException {
        while (!conditionMet) {
            wait();
        }
        System.out.println("Condition met, proceeding...");
    }

    public synchronized void signalCondition() {
        conditionMet = true;
        notify();
    }

    public static void main(String[] args) {
        ConditionWaitNotifyExample test = new ConditionWaitNotifyExample();

        Thread thread1 = new Thread(() -> {
            try {
                System.out.println("Thread 1 waiting for condition...");
                test.waitForCondition();
            } catch (InterruptedException e) {
                // Ignore
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                Thread.sleep(2000); // Simulate some work
                System.out.println("Thread 2 signaling condition...");
                test.signalCondition();
            } catch (InterruptedException e) {
                // Ignore
            }
        });

        thread1.start();
        thread2.start();
    }
}
