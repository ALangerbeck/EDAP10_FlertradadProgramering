# Lab 3 Notes

# Reflection

- R1. technically 3 different thread, but main thread only starts other threads. Question, what happens when main thread is done? lift, passengers. Monitor signaling
- R2. If it wakes up and the condition is not satisfied it will not wait again.
- R3. Usefull since we often want to to do something when a condition is not true, and it helps us think with that logic.
- R4. it will hog the monitor while the lift is moving, aka nothing else will happen. 
- R5. If a method changes x it needs to notify.
- R6. Java awns: only the lock owner can call notify. Multiple threads can notify at the same time. Don't know which synchronize to notify??
