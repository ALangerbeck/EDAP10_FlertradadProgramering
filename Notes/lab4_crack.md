# R1. Which threads exist in your application?
main, two threads in pool, EDT (swing) (sniffer ? )

# R2. You have a number of callbacks in your solution (such as onMessageIntercepted() and onProgress()). Which threads call these callbacks?
on message on_message intercept, onProgress thread pools

# R3. What shared data is there, and how do you ensure only one thread accesses it at a time?
graphic elements, only EDT edits constructs or reads. Edt tasks, are put in queue.

# R4. In the SnifferCallback interface method onMessageIntercepted, your application receives two refer­ences: one to a String, the other to a BigInteger. Strings and BigIntegers are both immutable. Why is this important here?
No need to worry about the data being changed by a thread

# R5. What happens when you execute a long­running task within the EDT in Swing?
It will hog EDT Time

# R6. What are the advantages of using a thread pool? What reasons can you see for a limited pool size (number of threads) in this application?
No need to code a thread safe queue. Thread pool handles that for us. Limit resource usage, maybe overhead becomes to bg.

# R7. In item I11, you found a Future method returning a boolean. In your application, in which situation(s) would the method return false?
if its already completed

# R8. How have you used thread confinement in this lab?
swing invoke later.