import java.util.concurrent.Semaphore;
//import java.util.concurrent.Semaphore;

public class Monitor {
    private Semaphore alarm_time_semaphore = new Semaphore(1);
    private Semaphore alarm_toggle_semaphore = new Semaphore(1);
    private Semaphore current_time_semaphore = new Semaphore(1);

    // private long current_time;
    private long current_h;
    private long current_m;
    private long current_s;
    private long alarm_time;
    private boolean alarm_toggle;

    public Monitor() {

    }

    public long get_current_time() throws InterruptedException {
        current_time_semaphore.acquire();
        long return_val = current_h * 3600000 + current_m * 60000 + current_s * 1000;
        current_time_semaphore.release();
        return return_val;
    }

    public long increment_current_time() throws InterruptedException {
        current_time_semaphore.acquire();
        current_s = (current_s + 1) % 60;
        if (current_s == 0) {
            current_m = (current_m + 1) % 60;
            if (current_m == 0) {
                current_h = (current_h + 1) % 24;
            }
        }
        long return_time = current_h * 3600000 + current_m * 60000 + current_s * 1000;
        current_time_semaphore.release();
        return return_time;

    }

    public void set_current_time(int hours, int minutes, int seconds) throws InterruptedException {
        current_time_semaphore.acquire();
        current_h = hours;
        current_m = minutes;
        current_s = seconds;
        current_time_semaphore.release();
    }

    public void set_alarm(int hours, int minutes, int seconds) throws InterruptedException {
        alarm_time_semaphore.acquire();
        alarm_time = hours * 3600000 + minutes * 60000 + seconds * 1000;
        alarm_time_semaphore.release();
    }

    public boolean alarm_toggle() throws InterruptedException {
        alarm_toggle_semaphore.acquire();
        this.alarm_toggle = !this.alarm_toggle;
        boolean return_bool = this.alarm_toggle;
        alarm_toggle_semaphore.release();
        return return_bool;
    }

    public boolean check_alarm() throws InterruptedException {
        alarm_toggle_semaphore.acquire();
        boolean return_bool = alarm_toggle;
        alarm_toggle_semaphore.release();
        return return_bool;
    }

    public long alarm_time() throws InterruptedException {
        return alarm_time;
    }

}
