package org.educative.concurrency;

import lombok.AllArgsConstructor;
import lombok.ToString;

import java.util.EmptyStackException;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

class NonBlockingStack<T> {
    @AllArgsConstructor
    @ToString
    class Node {
        T val;
        Node next;
    }

    AtomicInteger size = new AtomicInteger(0);
    AtomicReference<Node> head = new AtomicReference<>();

    public void push(T val) {
        Node existingHead;
        Node newHead;
        do {
            existingHead = head.get();
            newHead = new Node(val, existingHead);
        } while(! head.compareAndSet(existingHead, newHead));
        size.incrementAndGet();
    }

    public T pop() {
        Node existingHead;
        Node next;
        do {
            existingHead = head.get();
            if (existingHead == null) {
                throw new EmptyStackException();
            }
            next = existingHead.next;
        } while(! head.compareAndSet(existingHead, next));
        size.decrementAndGet();
        return existingHead.val;
    }

    public int size() {
        System.out.println(head.get());
        return size.get();
    }

    public static void main( String args[]) throws Exception {

        NonBlockingStack<Integer> stack = new NonBlockingStack<>();
        ExecutorService executorService = Executors.newFixedThreadPool(20);

        for (int i = 0; i < 20; i++) {
            executorService.submit(() -> {
                for (int i1 = 0; i1 < 10000; i1++) {
                    stack.push(i1);
                }

                for (int i1 = 0; i1 < 10000; i1++) {
                    stack.pop();
                }
            });
        }
        Thread.sleep(10000);
        executorService.shutdown();
        System.out.println("Number of elements in the stack = " + stack.size());
    }
}