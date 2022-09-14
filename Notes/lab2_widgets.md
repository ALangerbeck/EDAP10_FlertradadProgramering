# Lab 2 Widgets

## Implementation 

### 15:
    - A: The threads who is blocked will busy wait and hold the tread
    - B: Recognized from 12. The last working train is faster since the monitor class does not lock up from other trains.
    - C: Trains will only wait one time, crashes will happen. Only one train will wake.
    - D: Trains will block the train that blocks the in tight intersections. Problems how the route is structured. There is no safe lenght depending on how tight the tracks are laid. In the simulations 4 seems to be the largest non blocking train.

### 16: 
    -A demonstrates busy-wait 
    -B race conditions
    -C race conditions
    -D deadlock

## Reflection:
### part 1
    - R1: Set with busy segments
    - R2: The train crashed, error thingy
    - R3: monitor synchronized set
    - R4: Another thread can slip in between wait until free and mark busy. Both bitch! We assume that the methods are synchronized.
    - R5: wait, releases the synchronized lock!!!
    - R6: Deadlock, train blocking trains, so that they block trains, which block trains, to block the first train. N depends on the track layout. Usually 4.
    - R7: the trains could enter from either side and block each other front to front.
### part 2
    - R8: one thread was enoughs
    - R9: Monitor, both tools can stop the thread, but both need to be done before the conveyor starts,we kept track of how many tools where working and only started if no one worked.
    - R10: conveyorbelt
    - R11: we used wait notify, a thread could just leave the monitor and the last thread starts the conveyor again
    - R12: yes we only have two working threads. disadvantage, harder to add machinery.
### General
    - R13
      - unexpected values, crashes when order of things are important (thread taking things out of empty list that should not be empty)
      - nothing happening, with some threads
      - nothing happening with any threads
    - R14 Usually no since there is no guarantee that the thread which waited is the next thread to run.  Can work when two threads passing work to each other.
    - R15 We run long periods of time att high workload. No
