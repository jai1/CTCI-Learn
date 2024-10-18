<!-- TOC -->
* [Concepts](#concepts)
  * [Missed Signals](#missed-signals)
  * [Spurious wakes](#spurious-wakes)
  * [Thread life cycle](#thread-life-cycle)
  * [Thread Priority:](#thread-priority-)
  * [Daemon Threads:](#daemon-threads-)
  * [Task](#task)
  * [Monitors:](#monitors)
  * [Deadlocks:](#deadlocks-)
  * [Race Conditions:](#race-conditions-)
  * [Starvation:](#starvation)
  * [Fairness:](#fairness-)
  * [Spinning:](#spinning-)
  * [Live locks:](#live-locks)
  * [Debugging](#debugging)
    * [Debugging Tools](#debugging-tools)
    * [Effective Strategies](#effective-strategies)
    * [Specific Tools and Techniques](#specific-tools-and-techniques)
* [Java Concurrency Constructs](#java-concurrency-constructs)
  * [Volatile](#volatile)
  * [Atomics](#atomics)
  * [Adders and Accumulators](#adders-and-accumulators)
  * [Reentrant Lock](#reentrant-lock)
  * [Synchronized](#synchronized)
  * [CompleteableFuture](#completeablefuture)
  * [Conditional Variable](#conditional-variable)
  * [Semaphore](#semaphore)
  * [Countdown latch](#countdown-latch-)
  * [CyclicBarrier](#cyclicbarrier)
  * [Executor Framework](#executor-framework)
  * [ExecutorCompletionService](#executorcompletionservice)
  * [ThreadLocal](#threadlocal)
  * [Concurrent Collections in Java](#concurrent-collections-in-java)
  * [Stamped lock](#stamped-lock)
* [Custom Structures](#custom-structures)
  * [Thread Safe singleton with lazy initialization](#thread-safe-singleton-with-lazy-initialization)
  * [Semaphore with max permit.](#semaphore-with-max-permit)
  * [Token Bucket](#token-bucket)
  * [Deferred callback](#deferred-callback)
  * [Read write lock](#read-write-lock)
  * [Non blocking Stack](#non-blocking-stack)
  * [Multi-threaded Quick Sort](#multi-threaded-quick-sort)
  * [Course](#course)
  * [Document](#document)
<!-- TOC -->

# Concepts

## Missed Signals
A missed signal happens when a `notify` / `signal` is sent by a thread before the other thread starts waiting on a condition. A `semaphore` is used to solve this issue.

Even though you have a synchronized block and a while loop, there's a tiny, almost imperceptible window of time where the following sequence could occur:

- Thread A (waiting): Acquires the lock, enters the synchronized block, and checks the condition in the while loop (finds it false).
- Thread B (signaling):  Acquires the lock (since Thread A is about to release it to enter wait()), sets the condition to true, and calls notify().  Since Thread A hasn't yet called wait(), there's no thread waiting to be notified, so the notification is effectively lost.
- Thread A (waiting):  Releases the lock and calls wait(). Now, it's waiting, but the notification has already been sent and missed.

For most practical applications, the combination of synchronized and a while loop provides sufficient protection against missed signals. However to be a 100% certain use a `semaphore`.

https://stackoverflow.com/questions/79095630/missed-signal-in-a-java

## Spurious wakes
A spurious wakeup means a thread is woken up even though no signal has been received. Spurious wakeups are a reality and are one of the reasons why the pattern for waiting on a condition variable happens in a while loop as discussed in earlier chapters. Both `wait()` and conditional variables suffer from it.

## Thread life cycle
In Java, a thread goes through the following life cycle stages:
- **New:** Thread is created but not yet started (`new Thread()`).
- **Runnable:** After `start()`, the thread is ready to run, waiting for CPU time.
- **Blocked:** The thread is waiting for a resource or lock (e.g., `wait()`, `synchronized`).
- **Timed Waiting:** The thread is waiting for a specified time (e.g., `sleep()`, `join(timeout)`).
- **Terminated:** The thread has completed execution and cannot run again.

## Thread Priority: 
Each thread has a priority that influences its execution relative to other threads. Higher priority threads are generally executed preferentially.
## Daemon Threads: 
These are low-priority threads that provide services to us´er threads. They do not prevent the JVM from exiting. When the main thread exits, the JVM also kills any threads marked daemon.
[Example](./DaemonThreadExample.java)

## Task
A task is a logical unit of work. Usually, a task should be independent of other tasks so that it can be completed by a single thread. A task can be represented by an object of a class implementing the Runnable interface.

## Monitors:
Java's built-in synchronization mechanism associated with every object. It uses locks to provide mutual exclusion. Four method provided by **all** java objects are `lock`, `notify`, `notifyAll` and `wait`

- `wait` and `notify` is used when a single thread has access (or waiting) to a resource at a time. [Example](./CountingSemaphore.java) and [Example](./ConditionWaitNotifyExample.java)
- `notifyAll` (or `signalAll` for conditional variables) is used when there are multiple threads waiting at the same time, like in [ReadWriteLock](./ReadWriteLock.java) and [CustomCyclicBarrier](./CyclicBarrierTest.java). 

## Deadlocks: 
A situation where two or more threads are blocked forever, each waiting for the other to release a resource.

## Race Conditions: 
Occur when multiple threads access and modify shared data simultaneously, leading to unpredictable results.

## Starvation:
A thread is perpetually denied access to resources and cannot make progress.
## Fairness: 
A scheduling policy that ensures threads get a fair chance to execute, preventing starvation.
## Spinning: 
A technique where a thread repeatedly checks a condition instead of blocking, potentially wasting CPU cycles.
## Live locks:
Threads are actively responding to each other but not making progress, similar to a deadlock but with constant activity.

## Debugging
Debugging threading issues is difficult and requires patience and methodological approach.
### Debugging Tools

* **Debuggers**
  * **Breakpoints:** Pause execution at specific points to inspect variables and thread states.
  * **Conditional Breakpoints:** Trigger breakpoints based on specific conditions.
  * **Stepping:** Execute code line by line or step into/over methods.
  * **Thread View:** Inspect the state of each thread, including its call stack and local variables.
* **Logging**
  * **Detailed Logs:** Include timestamps, thread IDs, and relevant data.
  * **Log Levels:** Use different log levels (e.g., DEBUG, INFO, ERROR) to control verbosity.
* **Profilers**
  * **CPU Profiling:** Identify performance bottlenecks.
  * **Thread Profiling:** Visualize thread interactions and identify potential contention.
  * **Memory Profiling:** Detect memory leaks or excessive memory usage.

### Effective Strategies

* **Reproduce Consistently:** Identify the steps or conditions that reliably trigger the problem.
* **Simplify the Code:** Isolate the problematic section of code.
* **Synchronize Carefully:** Use appropriate synchronization mechanisms (locks, semaphores, etc.).
* **Avoid Deadlocks:** Acquire locks in a consistent order and release them promptly.
* **Test with Different Thread Counts:** Vary the number of threads to observe behavior under different loads.
* **Use Static Analysis Tools:**  Tools like FindBugs can help identify potential threading issues.

### Specific Tools and Techniques

* **VisualVM:** A powerful tool for monitoring and profiling Java applications, including thread analysis.
* **JConsole:**  Another JDK tool for monitoring JVM performance and resource consumption.
* **Thread Dumps:** Snapshots of the state of all threads in the JVM.
* **Stress Testing:** Subject your application to heavy loads to uncover hidden concurrency problems.

# Java Concurrency Constructs
## Volatile


If a variable is declared `volatile` then whenever a thread writes or reads to the `volatile` variable, the read and write always happen in the main memory.

**Visibility:** When a thread modifies a `volatile` variable, the change is immediately visible to all other threads. Without `volatile`, each thread might keep a local copy of the variable in its cache, leading to inconsistencies.

**No Atomicity:** `volatile` ensures visibility, but it doesn't make operations on the variable atomic. If you need to perform multiple operations on a `volatile` variable as a single, indivisible step (like incrementing it), you'll still need synchronization mechanisms like locks.



**Single Writer, Multiple Readers:** `volatile` is sufficient.

**Multiple Writers:** `volatile` Not Sufficient. Synchronized is necessary: If multiple threads might write to the variable, you need synchronized to ensure atomicity. volatile alone won't prevent race conditions where threads might overwrite each other's changes, leading to data corruption.

## Atomics

Atomics provide a way to perform thread-safe operations on variables without traditional locks, ensuring operations happen as a single, indivisible unit. This prevents race conditions when multiple threads access the same variable.

Java offers atomic classes like `AtomicInteger`, `AtomicLong`, `AtomicBoolean`, and `AtomicReference` in the `java.util.concurrent.atomic` package.

**Key Idea: Compare-and-Swap (CAS)**

Many atomic classes use CAS. This operation atomically compares the current value of a variable with an expected value and updates it only if they match. This ensures that changes are made based on the latest state of the variable, avoiding lost updates.

**Note:** 
1. Atomics are not primitvies
The following widget highlights these differences between Integer and AtomicInteger. The Integer class has the same hashcode for the same integer value but that’s not the case for AtomicInteger. Thus Atomic* scalar classes are unsuitable as keys for collections that rely on hashcode.
2. It has been observed that atomics perform better than locks under low to moderate contention. 

Atomics are excellent for simple concurrent operations, but for more complex scenarios, you might need other synchronization mechanisms.

[Example](./NonBlockingStack.java)

## Adders and Accumulators
`LongAdder` and `LongAccumulator` are faster than `AtomicLong` however they need more memory.

- AtomicLong: Simple, low overhead.  Bad for high contention.
- LongAdder:  Great for high contention, simple additions. More overhead.
- LongAccumulator:  High contention, custom logic. Complex to use.

## Reentrant Lock
[Link to example](./ReentrantLockExample.java)

**Note:** 
1. If you attempt to unlock a reentrant lock object by a thread which didn't lock it initially, you'll get an `IllegalMonitorStateException`.

## Synchronized
Synchronized is like a Re-enterant lock *except* that it doesn't provide `tryLock` capabilities. Instead it is simpler to read and use. It also guarantees unlock. Synchronized is implemented using java monitors.

## CompleteableFuture
CompletableFuture lets your Java program do things in the background without waiting, making it faster and more responsive.

- How to make recursion work multithreaded using `supplyAssync`, `thenComposeAsync` and `allOf`. Example: [MultiThreadedQuickSort](./MultiThreadedQuickSort.java)


## Conditional Variable
[Link to example](./ConcurrentBoundedBuffer.java)
Each java object exposes the three methods `wait()`,`notify()` and `notifyAll()` which can be used to suspend threads till some condition becomes true. You can think of Condition as factoring out these three methods of the object monitor into separate objects so that there can be _multiple wait-sets_ per object.
**Note:** Use the `signal()` and `await()` methods of thises conditional variable. Not `wait()`,`notify()` and `notifyAll()`.


## Semaphore
Java does provide its own implementation of Semaphore. It supports two methods. `release()` to put permit back into the pool. `acquire()` to get permits or block if none available. 

**Note:** 
1. `release()` can be called as many times as you want before `acquire()` hence accumulate infinite permits.
2. You can initialize semaphore with an initial count of permits.

[Example](./PrintNumberSeries.java)

## Countdown latch 
A CountDownLatch in Java is a synchronization aid that allows one or more threads to wait until a set of operations being performed in other threads completes.

**Key characteristics:**
1. One-shot: A CountDownLatch is a one-time use mechanism. Once the count reaches zero, it cannot be reset.
2. Non-cyclic: Unlike CyclicBarrier, which can be reused, CountDownLatch is designed for scenarios where you need a one-time synchronization point.

## CyclicBarrier
CyclicBarrier is used to make threads wait for each other. It is used when different threads process a part of computation and when all threads have completed the execution, the result needs to be combined in the parent thread.

[Link](./CyclicBarrierTest.java)

## Executor Framework
In Java, the primary abstraction for executing logical tasks units is the Executor framework and not the Thread class. The classes in the Executor framework separate out:
- Task Submission
- Task Execution

The framework allows us to specify different policies for task execution. Java offers three interfaces, which classes can implement to manage thread lifecycle. These are:
- Executor Interface
- ExecutorService [Example](./NonBlockingStack.java) and [Example](./ConcurrentBoundedBuffer.java)
- ScheduledExecutorService
The Executor interface forms the basis for the asynchronous task execution framework in Java.
ExecutorService is also needed since creating and destroying new threads is time and memory intensive.

An Executor has the following stages in its lify-cycle:
- Running
- Shutting Down
- Terminated

## ExecutorCompletionService
ExecutorCompletionService is a class in Java that enhances the functionality of an ExecutorService for managing asynchronous tasks. It allows you to submit tasks to an executor and retrieve the results as they become available, in the order of completion, rather than the order of submission.   
[Example](./ExecutorCompletionServiceExample.java)

## ThreadLocal
In Java, ThreadLocal is a class that provides thread-local variables. These variables differ from regular variables in that each thread that accesses a ThreadLocal variable gets its own independently initialized copy of the variable.
[Example](./ReadWriteLock.java)

## Concurrent Collections in Java
Java provides a set of thread-safe collections in the `java.util.concurrent` package designed for concurrent access. These collections offer better performance and scalability compared to synchronizing standard collections.

Here's a list of common concurrent collections with short descriptions:

**1. ConcurrentHashMap:**

* A hash table supporting full concurrency of retrievals and adjustable expected concurrency for updates.
* Replaces `Hashtable` and `Collections.synchronizedMap(HashMap)`.
* Offers better performance with segmented locking.

**2. ConcurrentSkipListMap:**

* A scalable concurrent `ConcurrentNavigableMap` implementation.
* Sorted according to the natural ordering of its keys, or by a `Comparator` provided at map creation time.

**3. ConcurrentSkipListSet:**

* A scalable concurrent `NavigableSet` implementation.
* Uses `ConcurrentSkipListMap` for its internal structure.

**4. CopyOnWriteArrayList:**

* A thread-safe variant of `ArrayList` where all mutative operations (add, set, and so on) are implemented by making a fresh copy of the underlying array.
* Excellent for read-heavy situations with infrequent modifications.

**5. CopyOnWriteArraySet:**

* A `Set` that uses an internal `CopyOnWriteArrayList` for all of its operations.
* Similar performance characteristics to `CopyOnWriteArrayList`.

**6. ConcurrentLinkedQueue:**

* An unbounded thread-safe queue based on linked nodes.
* Offers good performance for FIFO (first-in, first-out) operations.

**7. ConcurrentLinkedDeque:**

* An unbounded thread-safe deque based on linked nodes.
* Supports efficient addition and removal at both ends.

**8. ArrayBlockingQueue:**

* A bounded blocking queue backed by an array.
* Blocks when the queue is full or empty.

**9. LinkedBlockingQueue:**

* An optionally-bounded blocking queue based on linked nodes.
* Can be bounded or unbounded.

**10. PriorityBlockingQueue:**

* An unbounded blocking queue that orders elements according to their natural ordering or by a `Comparator` provided at queue construction time.

**11. DelayQueue:**

* A blocking queue of `Delayed` elements, in which an element can only be taken when its delay has expired.

**12. SynchronousQueue:**

* A blocking queue in which each insert operation must wait for a corresponding remove operation by another thread, and vice versa.

These concurrent collections provide efficient and thread-safe ways to manage data in concurrent applications, offering various features and performance characteristics to suit different use cases.

## Stamped lock
StampedLock is like a library with different access levels:

- Read: Many people can read books at once.
- Write: Only one person can change the catalog at a time.
- Optimistic Read: Quickly check if the catalog is being changed. If not, assume it's safe to read.

[Example](./StampedLockOptimisticRead.java)

# Custom Structures

## Thread Safe singleton with lazy initialization
Lazy initialization means that we initialize the object only if getInstance is called.
```
public class Singleton {
    private static Singleton instance;

    private Superman() {
    }

    public synchronized static Singleton getInstance() {

        if (instance == null) {
            instance = new Singleton();
        }

        return instance;
    }
}
```

## Semaphore with max permit.
Java's semaphore can be initialized with _only_ an initial number of permits. We will create a semaphore that can take max permit count as well. Hence `release()` function will also block.

[Link](./CountingSemaphore.java)

## Token Bucket
Imagine you have a bucket that gets filled with tokens at the rate of 1 token per second. The bucket can hold a maximum of N tokens. Implement a thread-safe class that lets threads get a token when one is available. If no token is available, then the token-requesting threads should block. The class should expose an API called getToken that various threads can call to get a token.

[Link](./TokenBucketFilter.java)

## Deferred callback
Design and implement a thread-safe class that allows registeration of callback methods that are executed after a user specified time interval in seconds has elapsed.

[Link](./DeferredCallbackExecutor.java)

## Read write lock
Implement a read write lock. Write lock needs to wait for all readers to finish reading. However if there is a request for writeLock, read locks requests need to be de-prioritized (wait) till write is complete. There can be only one writeLock on the service at a time. There can be multiple readlocks on the service at the same time.

**Note:** Here signal all needs to be used since multiple reader threads are waiting at the same time.

[Link](./ReadWriteLock.java)

## Non blocking Stack
[Link](./NonBlockingStack.java)

## Multi-threaded Quick Sort

[Link](./MultiThreadedQuickSort.java)

## Course
[Java Multithreading for Senior Engineering Interviews](https://www.educative.io/courses/java-multithreading-for-senior-engineering-interviews/)

## Document
[Concurrency Interview Questions](https://docs.google.com/document/d/1EenpvQnDPIWlsmZSzTesF0JJEjCS2EllLIb6G3-oDgE/edit?tab=t.0#heading=h.kbjghy3jz261)