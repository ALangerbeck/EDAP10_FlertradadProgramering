# Design Tasks
1. The main method (sketched above) handles user input. What additional thread(s) do you need, beyond
this main thread?
- Timer, actually counting and increasing time
- ? handles actual input ?

2. What common data needs to be shared between threads? Where is the data to be stored?
Hint: introduce a dedicated class for this shared data, as outlined above.

3. For each of your threads, consider:
• What operations on shared data are needed for the thread?
• Where in the code is this logic best implemented?


1. In which parts of your code is data accessed concurrently from different threads? Where in your code
do you need to ensure mutual exclusion?
5. Are there other situations in the alarm clock where semaphores are to be used?
Hint: have a look at ClockInput in section 1.1.2.