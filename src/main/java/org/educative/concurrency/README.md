<!-- TOC -->
* [Concepts](#concepts)
  * [Missed Signals](#missed-signals)
* [Java Concurrency Constructs](#java-concurrency-constructs)
  * [Volatile](#volatile)
  * [Reentrant Lock](#reentrant-lock)
  * [Synchronized](#synchronized)
  * [Condition Variable](#condition-variable)
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
These are low-priority threads that provide services to user threads. They do not prevent the JVM from exiting.

## Monitors:
Java's built-in synchronization mechanism associated with every object. It uses locks to provide mutual exclusion.

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
## Livelocks:
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

## Reentrant Lock
[Link to example](./ReentrantLockExample.java)

**Note:** 
1. If you attempt to unlock a reentrant lock object by a thread which didn't lock it initially, you'll get an `IllegalMonitorStateException`.

## Synchronized
Synchronized is like a ReEnterant lock *except* that it doesn't provide `tryLock` capabilities. Instead it is simpler to read and use. It also guarantees unlock. Synchronized is implemented using java monitors.

## Condition Variable
[Link to example](./ConcurrentBoundedBuffer.java)
Each java object exposes the three methods `wait()`,`notify()` and `notifyAll()` which can be used to suspend threads till some condition becomes true. You can think of Condition as factoring out these three methods of the object monitor into separate objects so that there can be _multiple wait-sets_ per object.



# Reference
## Course
[Java Multithreading for Senior Engineering Interviews](https://www.educative.io/courses/java-multithreading-for-senior-engineering-interviews/)

## Document
[Concurrency Interview Questions](https://docs.google.com/document/d/1EenpvQnDPIWlsmZSzTesF0JJEjCS2EllLIb6G3-oDgE/edit?tab=t.0#heading=h.kbjghy3jz261)