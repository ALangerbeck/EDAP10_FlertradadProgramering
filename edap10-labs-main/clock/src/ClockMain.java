import clock.AlarmClockEmulator;
import clock.io.ClockInput;
import clock.io.ClockInput.UserInput;
import clock.io.ClockOutput;

public class ClockMain {

    public static void main(String[] args) throws InterruptedException {
        final boolean DEBUG = false;
        AlarmClockEmulator emulator = new AlarmClockEmulator();
        Monitor monitor = new Monitor();

        ClockInput in = emulator.getInput();
        ClockOutput out = emulator.getOutput();

        out.displayTime(0, 0, 0); // arbitrary time: just an example

        display_update display_update_thread = new display_update(out, monitor);
        display_update_thread.start();

        alarm_thread alarm_thread = new alarm_thread(out, monitor);
        alarm_thread.start();

        while (true) {
            in.getSemaphore().acquire();
            UserInput userInput = in.getUserInput();
            int choice = userInput.getChoice();
            int h = userInput.getHours();
            int m = userInput.getMinutes();
            int s = userInput.getSeconds();
            switch (choice) {
                case 1:
                    monitor.set_current_time(h, m, s);
                    break;
                case 2:
                    monitor.set_alarm(h, m, s);
                    break;
                case 3:
                    out.setAlarmIndicator(monitor.alarm_toggle());
                    break;

            }
            if (DEBUG) {
                System.out.println("choice=" + choice + " h=" + h + " m=" + m + " s=" + s);
            }
        }
    }

}
