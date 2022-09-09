import clock.io.ClockOutput;

class display_update extends Thread {
    ClockOutput clock_out;
    Monitor monitor;

    display_update(ClockOutput clock_out, Monitor monitor) {
        this.clock_out = clock_out;
        this.monitor = monitor;

    }

    @Override
    public void run() {
        int increments = 0;
        long start_time = System.currentTimeMillis();
        long delta;
        long clock_time = 0;
        try {
            while (true) {
                long system_time = System.currentTimeMillis();
                delta = system_time - start_time;
                Thread.sleep(1000 - (delta - 1000 * increments));
                increments++;

                clock_time = monitor.increment_current_time();

                if (monitor.check_alarm() && clock_time == monitor.alarm_time()) {
                    alarm_thread alarm_thread = new alarm_thread(clock_out, monitor);
                    alarm_thread.start();
                }
                int current_hour = (((int) clock_time / 1000) / 3600);
                int current_minute = (((int) clock_time / 1000) / 60) % 60;
                int current_seconds = ((int) clock_time / 1000) % 60;

                clock_out.displayTime(current_hour, current_minute,
                        current_seconds);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

}