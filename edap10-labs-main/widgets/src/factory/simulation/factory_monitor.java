package factory.simulation;

import java.util.concurrent.Semaphore;

import factory.model.Conveyor;
import factory.model.Tool;

public class factory_monitor {
    private Conveyor conveyor;
    private int conveyor_use;
    
    public factory_monitor(Conveyor conveyor){
        this.conveyor =  conveyor;
        conveyor_use = 0;
    }
    public synchronized void stop_conveyor() throws InterruptedException {
        conveyor_use += 1;
        conveyor.off();
    }

    public synchronized void start_conveyor() throws InterruptedException {
        conveyor_use -= 1;
        notifyAll();
        while(conveyor_use != 0){
            wait();
        }
        conveyor.on();
        
    }
    
}
