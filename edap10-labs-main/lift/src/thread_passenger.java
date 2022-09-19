import lift.Passenger;
import lift.LiftView;

public class thread_passenger extends Thread {

    private Passenger passenger;
    private monitor_lift monitor;
    public thread_passenger(LiftView view,monitor_lift monitor){
        this.passenger = view.createPassenger();
        this.monitor = monitor;
    }

    @Override
    public void run(){
        try{
            while(true){
            passenger.begin();
            int floor = passenger.getStartFloor(); 
            monitor.wait_for_lift(floor);
            }
        }
        catch(InterruptedException e){

        }
    }
    
}
