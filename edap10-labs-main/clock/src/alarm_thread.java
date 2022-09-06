import clock.io.ClockOutput;

class alarm_thread extends Thread {
    ClockOutput clock_out;
    Monitor monitor;

    alarm_thread(ClockOutput clock_out, Monitor monitor) {
        this.clock_out = clock_out;
        this.monitor = monitor;
    }

    @Override
    public void run() {
        try {
            long t0 = System.currentTimeMillis();
            for (int i = 0; i < 20; i++) {
                if (!(monitor.check_alarm())) {
                    break;
                }
                long now = System.currentTimeMillis();
                long delta = now - t0;
                if (i % 2 == 0) {
                    clock_out.alarm();
                }
                Thread.sleep(1000 - (delta - 1000 * i));
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}