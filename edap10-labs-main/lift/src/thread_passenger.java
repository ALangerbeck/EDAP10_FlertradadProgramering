import lift.Passenger;

import javax.swing.text.View;

import lift.LiftView;

public class thread_passenger extends Thread {

    private LiftView view;
    private monitor_lift monitor;
    public thread_passenger(LiftView view,monitor_lift monitor){
        this.view = view;
        this.monitor = monitor;
    }

    @Override
    public void run(){
        try{
            while(true){
                Passenger passenger = view.createPassenger();
                passenger.begin();
                monitor.wait_for_lift(passenger);
                monitor.wait_to_leave(passenger);
                passenger.end();
            }
        }
        catch(InterruptedException e){

        }
    }
    
}
