package org.educative.concurrency;

public class DaemonThreadExample {
    public static void main(String[] args) throws InterruptedException {
        Thread daemonThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                    System.out.println("Daemon thread is running...");
                } catch (InterruptedException e) {
                    System.out.println("Daemon thread interrupted");
                    break; // Exit the loop when interrupted
                }
            }
        });

        // Set the thread as a daemon thread
        daemonThread.setDaemon(true);

        daemonThread.start();

        Thread.sleep(3000);

        // Interrupt the daemon thread
        daemonThread.interrupt();

        System.out.println("Main thread exiting...");
    }
}