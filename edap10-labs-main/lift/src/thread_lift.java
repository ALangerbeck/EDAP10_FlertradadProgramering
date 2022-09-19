import lift.LiftView;

public class thread_lift extends Thread{

    private LiftView view;
    private int NBR_FLOORS; 
    private monitor_lift monitor;

    public thread_lift(LiftView view,int NBR_FLOORS,monitor_lift monitor){
        this.view = view;
        this.NBR_FLOORS = NBR_FLOORS;
        this.monitor = monitor;
    }   

    @Override
    public void run(){
        try{
            int to_floor = 0;
            int current_floor = 0;
            boolean going_up = true;
            while(true){
                if (going_up){
                    to_floor++;
                }else{
                    to_floor--;
                }
                if(to_floor == 0 || to_floor == NBR_FLOORS - 1){
                    going_up = !going_up;
                }
                view.moveLift(current_floor, to_floor);
                monitor.open_for_passengers(to_floor);
                current_floor = to_floor;
            }
        }catch(InterruptedException e){
            
        }
    }
    
}
