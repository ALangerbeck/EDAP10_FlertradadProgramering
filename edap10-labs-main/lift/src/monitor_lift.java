
import lift.LiftView;
import lift.Passenger;

public class monitor_lift {
    private LiftView view;
    private int[] to_enter;
    private int[] to_exit;
    private  int current_floor;
    private boolean doors_open; 
    private int MAX_PASSENGERS;
    private int number_in_lift = 0;
 
    public monitor_lift(LiftView view,int NBR_FLOORS,int max_passenger){
        to_enter = new int[NBR_FLOORS];
        to_exit = new int[NBR_FLOORS];
        this.view = view;
        this.MAX_PASSENGERS = max_passenger;


    }

    public synchronized void wait_for_lift(Passenger pass) throws InterruptedException{
        to_enter[pass.getStartFloor()]++;
        while(current_floor != pass.getStartFloor() && !doors_open/*|| number_in_lift == MAX_PASSENGERS*/){
            wait();
        }
        pass.enterLift();
        number_in_lift++;
        to_exit[pass.getDestinationFloor()]++;
        to_enter[pass.getStartFloor()]--;
        notifyAll();
    }

    public synchronized void wait_to_leave(Passenger pass) throws InterruptedException{
        while(true){
            wait();
        }
    }

    public synchronized void open_for_passengers(int floor) throws InterruptedException{
        if(to_enter[floor] != 0 /*&& number_in_lift < MAX_PASSENGERS*/){
            view.openDoors(floor);
            doors_open = true;
            current_floor = floor;
            notifyAll();
        }
        while(to_enter[floor] != 0 /* && number_in_lift < MAX_PASSENGERS*/){
            wait();
        }
        if(doors_open){
            doors_open = false;
            view.closeDoors();
        }
    }

        
}
