package wash.control;

import actor.ActorThread;
import wash.io.WashingIO;
import wash.simulation.WashingSimulator;

public class Wash {

    public static void main(String[] args) throws InterruptedException {
        WashingSimulator sim = new WashingSimulator(Settings.SPEEDUP);

        WashingIO io = sim.startSimulation();

        TemperatureController temp = new TemperatureController(io);
        WaterController water = new WaterController(io);
        SpinController spin = new SpinController(io);

        WashingProgram1 program1 = new WashingProgram1(io, temp, water, spin);
        WashingProgram2 program2 = new WashingProgram2(io, temp, water, spin);
        WashingProgram3 program3 = new WashingProgram3(io, temp, water, spin);

        temp.start();
        water.start();
        spin.start();

        while (true) {
            int n = io.awaitButton();
            System.out.println("user selected program " + n);

            // TODO:
            // if the user presses buttons 1-3, start a washing program
            // if the user presses button 0, and a program has been started, stop it

            switch (n) {
                case 0:
                    program1.interrupt();
                    program1.join();
                    program2.interrupt();
                    program2.join();
                    program3.interrupt();
                    program3.join();

                    // WashingProgram0 program0 = new WashingProgram0(io, temp, water, spin);
                    // program0.start();
                    break;
                case 1:
                    program1 = new WashingProgram1(io, temp, water, spin);
                    program1.start();
                    break;
                case 2:
                    program2 = new WashingProgram2(io, temp, water, spin);
                    program2.start();
                    break;
                case 3:
                    program3 = new WashingProgram3(io, temp, water, spin);
                    program3.start();
                    break;
                default:
                    break;
            }
        }
    }
};
