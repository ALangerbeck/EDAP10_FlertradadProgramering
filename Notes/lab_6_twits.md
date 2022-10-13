# Lab 2 twits

# R1. So, how did you solve that bug?
Msg_store was not thread, safe. Added mutex and con_waits.

# R2. How does the output of a crashing C program compare to that of a Java exception printout?
It's Shit, Segmentation fault, can probably get better with flags?

# R3. Your test case made the server crash quite reliably (as it should). What about the crashes could suggest a concurrency problem?
Running with mayn thread causes crashes, while running with one two works.

# R4. In step I11 (page 49), you inspected a module to see whether it could possibly be thread­safe. What did you look for? How did you determine whether the module was thread­safe?
NOT threadsafe, it had no locks or any concurency fixes, shared data accsessed withiut locking.

# R5. How do the pthreads concepts of mutexes and condition variables relate to what you know from concurrency in Java?
pthread mutex == lock (reetrantlock), cond = wait notify, but not tied to monitor


# R6. Compare working with threads in C, to working with threads in Java. Which similarities and differences can you think of?
The concept is the same, java has infrastructure built wile in c you need to do a lot on your own.

# R7. Why did the server initially deadlock when you added the mutex?
BusyWaiting while holding a lock

# R8. Why is a pthreads condition variable always associated with a mutex?
They release their own lock while waiting.

# R9. The initial test (ServerTest) verified that the server responds correctly to input, but it was clearly not sufficient to identify the problem you solved. Why not? What can you learn from this about testing concurrent programs? Is it possible to rely on testing alone to determine whether a concurrent program is correct?
Problems might only occur during high load with many threads, since most problems occur when resources are used concurrently which migh not happen with a small number of threads. No but yes.