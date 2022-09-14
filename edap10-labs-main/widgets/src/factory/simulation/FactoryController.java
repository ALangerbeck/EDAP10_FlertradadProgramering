package factory.simulation;

import javax.management.monitor.Monitor;

import factory.model.Conveyor;
import factory.model.Tool;
import factory.model.Widget;

public class FactoryController {
    
    public static void main(String[] args) {
        Factory factory = new Factory();

        Conveyor conveyor = factory.getConveyor();

        factory_monitor monitor = new factory_monitor(conveyor);
        
        Tool press = factory.getPressTool();
        Tool paint = factory.getPaintTool();

        Thread blob_press = new Thread(() -> blob_press_thread(monitor, press));
        Thread marble_painter = new Thread (() -> marble_paint_thread(monitor, paint));
        blob_press.start();
        marble_painter.start();
        
    }

    private static void marble_paint_thread(factory_monitor monitor, Tool paint){
        try{
            while(true){
                paint.waitFor(Widget.ORANGE_MARBLE);
                monitor.stop_conveyor();
                paint.performAction();
                monitor.start_conveyor();
            }
        }catch(InterruptedException e){

        }
    
    }

    private static void blob_press_thread(factory_monitor monitor, Tool press){
        try{
            while(true){
                press.waitFor(Widget.GREEN_BLOB);
                monitor.stop_conveyor();
                press.performAction();
                monitor.start_conveyor();

            }
        }catch(InterruptedException e){

        }
    }
}
