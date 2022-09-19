
import lift.LiftView;
import lift.Passenger;

public class monitor_lift {
    private LiftView view;
    private int[] to_enter;
    private int[] to_exit;
    private  int current_floor;
    private boolean doors_open; 
 
    public monitor_lift(LiftView view,int NBR_FLOORS){
        to_enter = new int[NBR_FLOORS];
        to_exit = new int[NBR_FLOORS];
        this.view = view;

    }

    public synchronized void wait_for_lift(int floor) throws InterruptedException{
        to_enter[floor]++;
        while(current_floor != floor && !doors_open){
            wait();
        }
    }

    public synchronized void open_for_passengers(int floor) throws InterruptedException{
        if(to_enter[floor] != 0){
            view.openDoors(floor);
            doors_open = true;
        }
        while(to_enter[floor] != 0){
            wait();
        }
    }

        
}
