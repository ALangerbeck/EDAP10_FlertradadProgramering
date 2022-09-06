# Design Tasks
1. The main method (sketched above) handles user input. What additional thread(s) do you need, beyond
this main thread?
   - Timer, actually counting and increasing time

2. What common data needs to be shared between threads? Where is the data to be stored?
Hint: introduce a dedicated class for this shared data, as outlined above.
   - Displayed time

3. For each of your threads, consider:
• What operations on shared data are needed for the thread?
    - set time / increase time
• Where in the code is this logic best implemented?
    - monitor


4. In which parts of your code is data accessed concurrently from different threads? Where in your code
do you need to ensure mutual exclusion?

5. Are there other situations in the alarm clock where semaphores are to be used?
Hint: have a look at ClockInput in section 1.1.2.


# Reflection
Take some time to reflect on your work in this lab. In particular:
R1. Why is mutual exclusion needed in your program?
    Things happen at the same time, we have time sensitive tasks
R2. How can you use a Semaphore (or Lock) for mutual exclusion?
    A lock "locks down" a portion of the code so only a set number of thread
    (usually one) can run the code concurently. This is important so that you
    don't get problems like data-race.
R3. How can you use a Semaphore for signaling between threads?
    A semaphore remembers how many "permits" they have left which means that
    treads can signal something is done for other threads to pick
R4. How do you use the Monitor design pattern in your design?
    one clas which stores all data and runs all operations on data 
R5. What does it mean to say that a thread is blocked?
    Waiting for a lock to be released
R6. In your implementation work, tasks I5–I6, you encountered inconsistent output: a clock time value
that didn’t correspond to the time set. How could this inconsistency arise? How
can it be prevented?
    data races, data is viewed and edited by multiple thread in non-sequential
    order
    
R7. The test in step I5 runs for 30 seconds. Why does it have to run for so long? Can we guarantee that
this time is sufficient to find the race conditions we are looking for?
    no, its up to chance(scheduler, hardware ...)
If you are unsure, take the opportunity to discuss these questions with your lab teacher when you present
your work.