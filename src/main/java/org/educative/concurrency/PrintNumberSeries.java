package org.educative.concurrency;

import java.util.concurrent.*;

class PrintNumberSeries {
    private int n;
    private final Semaphore zeroSem = new Semaphore(1);
    private final Semaphore oddSem = new Semaphore(0);
    private final Semaphore evenSem = new Semaphore(0);

    public PrintNumberSeries(int n) {
        this.n = n;
    }

    public void printZero() {
        for (int i = 0; i < n; ++i) {
            try {
                zeroSem.acquire();
            }
            catch (Exception e) {
            }
            System.out.print("0");
            // release oddSem if i is even or else release evenSem if i is odd
            if (i % 2 == 0) {
                oddSem.release();
            } else {
                evenSem.release();
            }
        }
    }

    public void printEven() {
        for (int i = 2; i <= n; i += 2) {
            try {
                evenSem.acquire();
            }
            catch (Exception e) {
            }
            System.out.print(i);
            zeroSem.release();
        }
    }

    public void printOdd() {
        for (int i = 1; i <= n; i += 2) {
            try {
                oddSem.acquire();
            }
            catch (Exception e) {
            }
            System.out.print(i);
            zeroSem.release();
        }
    }
    public static void main(String[] args) {

        PrintNumberSeries printNumberSeries = new PrintNumberSeries(1000);

        Thread t1 = new Thread(printNumberSeries::printZero);
        Thread t2 = new Thread(printNumberSeries::printEven);
        Thread t3 = new Thread(printNumberSeries::printOdd);

        t2.start();
        t1.start();
        t3.start();
    }
}