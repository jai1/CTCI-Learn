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
A missed signal happens when a signal is sent by a thread before the other thread starts waiting on a condition. Semaphore can be used to prevent missed signals.

# Java Concurrency Constructs
## Volatile


If a variable is declared `volatile` then whenever a thread writes or reads to the `volatile` variable, the read and write always happen in the main memory.

**Visibility:** When a thread modifies a `volatile` variable, the change is immediately visible to all other threads. Without `volatile`, each thread might keep a local copy of the variable in its cache, leading to inconsistencies.

**No Atomicity:** `volatile` ensures visibility, but it doesn't make operations on the variable atomic. If you need to perform multiple operations on a `volatile` variable as a single, indivisible step (like incrementing it), you'll still need synchronization mechanisms like locks.



**Single Writer, Multiple Readers:** `volatile` is sufficient.

**Multiple Writers:** `volatile` Not Sufficient. Synchronized is necessary: If multiple threads might write to the variable, you need synchronized to ensure atomicity. volatile alone won't prevent race conditions where threads might overwrite each other's changes, leading to data corruption.

## Reentrant Lock
[Link to example](./ReentrantLockExample.java)

**Note:** 
1. If you attempt to unlock a reentrant lock object by a thread which didn't lock it initially, you'll get an `IllegalMonitorStateException`.

## Synchronized
Synchronized is like a ReEnterant lock *except* that it doesn't provide `tryLock` capabilities. Instead it is simpler to read and use. It also guarantees unlock. Synchronized is implemented using java monitors.

## Condition Variable
[Link to example](./ConcurrentBoundedBuffer.java)
Each java object exposes the three methods `wait()`,`notify()` and `notifyAll()` which can be used to suspend threads till some condition becomes true. You can think of Condition as factoring out these three methods of the object monitor into separate objects so that there can be _multiple wait-sets_ per object. 