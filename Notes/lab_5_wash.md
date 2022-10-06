# Lab 5 notes

# R1. Which threads exist in your solution?
Main, Wasser ,Spin, Temperaturen, programmen (0-3).

# R2. How do the threads communicate? Is there any shared data?
Messages, IO, refrences to other threads

# R3. For TemperatureController, we selected a period of 10 seconds. What could the downside of a too long or too short period be?
The longer we sleep the bigger the temperature change, if we sleep to much we cant stay in the temp gap.

# R4. What period did you select for WaterController? What could the downside of a too long or too short period be?
2 sec, if we sleep to long it will take a longer time, will fill up with water. To short wasted energy.

# R5. Do you use any BlockingQueue in your solution? How?
yes, it is used to implement actor thread.

# R6. How do you use Java’s interruption facility (interrupt(), InterruptedException)?
We interupt the program thread, Interupted exception catch is used to do things when the program is interupted

# R7. How do you ensure that the machine never heats unless there is water in it?
Before we heat we wait for acknolagment that there is water filled.

# R8. Suppose a washing program ends by turning the heat off and draining the machine of water. The heat is turned off by sending a WashingMessage to TemperatureController. How can you ensure that the heat has indeed been turned off before the washing program continues (and starts the drain pump)?
Wait for acknowledgment of heating is turned off.