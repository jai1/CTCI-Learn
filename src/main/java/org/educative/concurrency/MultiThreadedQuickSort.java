package org.educative.concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThreadedQuickSort<T extends Comparable<T>> {

    private final ExecutorService executor;
    public MultiThreadedQuickSort(int thread) {
        executor = Executors.newFixedThreadPool(5);
    }

    public void shutdown() {
        executor.shutdown();
    }

    public void sort(List<T> input) {
        sort(input, 0, input.size() - 1).join();
    }

    private CompletableFuture<Void> sort(List<T> input, int startIndex, int endIndex) {
        if (startIndex >= endIndex) {
            return CompletableFuture.completedFuture(null);
        }

        return CompletableFuture.supplyAsync(() -> partition(input, startIndex, endIndex), executor)
                .thenComposeAsync(pivotIndex -> {
                    CompletableFuture<Void> future1 = sort(input, startIndex, pivotIndex-1);
                    CompletableFuture<Void> future2 = sort(input, pivotIndex + 1, endIndex);
                    return CompletableFuture.allOf(future1, future2);
                }, executor);
    }

    private int partition(List<T> input, int startIndex, int endIndex) {
        T pivotElement = input.get(endIndex);
        int lessThanIndex = startIndex;
        int greaterThanEqualIndex =  endIndex-1;
        while (lessThanIndex < greaterThanEqualIndex) {
            T currElement = input.get(lessThanIndex);
            // currElement - pivotElement >= 0
            if (currElement.compareTo(pivotElement) >= 0) {
                // curr element >= pivotElement
                input.set(lessThanIndex, input.get(greaterThanEqualIndex));
                input.set(greaterThanEqualIndex, currElement);
                greaterThanEqualIndex--;
            } else {
                lessThanIndex++;
            }
        }
        // either lessThanIndex contains greater or Equal Element or greaterThanEqualIndex contains less element
        if (input.get(greaterThanEqualIndex).compareTo(pivotElement) < 0) {
            greaterThanEqualIndex++;
        }
        input.set(endIndex, input.get(greaterThanEqualIndex));
        input.set(greaterThanEqualIndex, pivotElement);

        return greaterThanEqualIndex;
    }

    public static void main(String[] args) throws InterruptedException {
        MultiThreadedQuickSort<Integer> quickSort = new MultiThreadedQuickSort<>(4);
        List<Thread> threads = new ArrayList<>();
        Random random = new Random();
        for (int j = 0; j<10; j++) {
            Thread t = new Thread(() -> {
                List<Integer> integers = new ArrayList<>();
                for (int i = 0; i < random.nextInt(1, 10000); i++) {
                    integers.add(random.nextInt());
                }
                quickSort.sort(integers);
                for (int i = 1; i < integers.size(); i++) {
                    if (integers.get(i - 1) > integers.get(i)) {
                        throw new RuntimeException("Incorrect sorting.");
                    }
                }
            });
            t.start();
            threads.add(t);
        }

        for (Thread t : threads) {
            t.join();
        }

        quickSort.shutdown();
    }
}
