import lift.LiftView;
import lift.Passenger;

public class lift_main {
    public static void main(String[] args) {
        final int NBR_FLOORS = 7, MAX_PASSENGERS = 4;

        LiftView  view = new LiftView(NBR_FLOORS, MAX_PASSENGERS);
        monitor_lift monitor = new monitor_lift(view, NBR_FLOORS);
        //Passenger pass = view.createPassenger();
        //int  fromFloor = pass.getStartFloor();
        //int    toFloor = pass.getDestinationFloor();

        thread_lift lift = new thread_lift(view,NBR_FLOORS,monitor);
        lift.start();

        thread_passenger passenger = new thread_passenger(view,monitor);
        passenger.start();
        
        /* 
        if (fromFloor != 0) {
            view.moveLift(0, fromFloor);
        }
        view.openDoors(fromFloor);
        pass.enterLift();                    // step inside

        view.closeDoors();
        view.moveLift(fromFloor, toFloor);   // ride lift
        view.openDoors(toFloor);

        pass.exitLift();                     // leave lift
        pass.end();                          // walk out (to the right)
        */
    }
}
