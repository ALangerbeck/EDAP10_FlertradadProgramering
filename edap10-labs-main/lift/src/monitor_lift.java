
import lift.LiftView;
import lift.Passenger;

public class monitor_lift {
    private LiftView view;
    private int[] to_enter;
    private int[] to_exit;
    private  int current_floor;
    private boolean doors_open; 
    private int MAX_PASSENGERS;
    private int NBR_FLOORS;
    private int entering = 0;
    private int exiting = 0;

 
    public monitor_lift(LiftView view,int NBR_FLOORS,int max_passenger){
        this.NBR_FLOORS = NBR_FLOORS;
        to_enter = new int[NBR_FLOORS];
        to_exit = new int[NBR_FLOORS];
        this.view = view;
        this.MAX_PASSENGERS = max_passenger;
    }

    public synchronized void wait_for_lift(Passenger pass) throws InterruptedException{
        notifyAll();
        to_enter[pass.getStartFloor()]++;
        while(!(current_floor == pass.getStartFloor() && doors_open) || sum_array(to_exit) == MAX_PASSENGERS){
            wait();
        }
        entering++;
        to_exit[pass.getDestinationFloor()]++;
    }


    public synchronized void signal_entered(Passenger pass){
        to_enter[pass.getStartFloor()]--;
        entering--;
        notifyAll();
    }

    public synchronized void wait_to_leave(Passenger pass) throws InterruptedException{
        while(!(current_floor == pass.getDestinationFloor() && doors_open)){
            wait();
        }
        to_exit[pass.getDestinationFloor()]--;
        exiting++;
    }
    public synchronized void signal_leave(Passenger pass){  
        exiting--;
        notifyAll();
    }

    public synchronized void open_for_passengers(int floor) throws InterruptedException{
        current_floor = floor;
        if((to_enter[floor] != 0 && MAX_PASSENGERS != (sum_array(to_exit))) || to_exit[floor] != 0 || entering != 0 || exiting != 0){
            view.openDoors(floor);
            doors_open = true;
            notifyAll();
        }
        while((to_enter[floor] != 0 && MAX_PASSENGERS != sum_array(to_exit)) || to_exit[floor] != 0 || entering != 0 || exiting != 0){
            wait();
        }
        if(doors_open){
            doors_open = false;
            view.closeDoors();
        }
    }

    public synchronized void wait_for_passenger() throws InterruptedException{
        while(sum_array(to_enter) + sum_array(to_exit) == 0){
            wait();
        }
    }

    private int sum_array(int[] array){
        int sum = 0;
        for(int i = 0;i < NBR_FLOORS;i++ ){
            sum += array[i];
        }
        return sum;
    }    
}
