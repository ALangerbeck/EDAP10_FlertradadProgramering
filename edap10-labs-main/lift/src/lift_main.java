import lift.LiftView;

public class lift_main {
    public static void main(String[] args) {
        final int NBR_FLOORS = 10, MAX_PASSENGERS = 5, NBR_PASSENGERS = 20;

        LiftView  view = new LiftView(NBR_FLOORS, MAX_PASSENGERS);
        monitor_lift monitor = new monitor_lift(view, NBR_FLOORS, MAX_PASSENGERS);

        thread_lift lift = new thread_lift(view,NBR_FLOORS,monitor);
        lift.start();
        Thread passengers[] = new thread_passenger[20];
        for (int i = 0; i < NBR_PASSENGERS; i++) {
            passengers[i] = new thread_passenger(view, monitor);
            passengers[i].start();
        }
    }
}
