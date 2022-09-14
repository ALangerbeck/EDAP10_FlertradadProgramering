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
