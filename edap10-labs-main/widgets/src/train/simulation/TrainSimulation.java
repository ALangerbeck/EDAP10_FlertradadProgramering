package train.simulation;

import java.util.LinkedList;
import train.model.Route;
import train.model.Segment;
import train.view.TrainView;

public class TrainSimulation {
    static int TRAIN_LENGHT = 3;

    public static void main(String[] args) {

        TrainView view = new TrainView();
        train_monitor monitor = new train_monitor();
        Thread[] trains = new Thread[20];
        for (int i = 0; i < 20; i++) {
            trains[i] = new Thread(() -> run_train(view, monitor));
            trains[i].start();
        }
        // Thread train1 = new Thread(() -> run_train(view, monitor));
        // train1.start();
        // Thread train2 = new Thread(() -> run_train(view, monitor));
        // train2.start();
        // Thread train3 = new Thread(() -> run_train(view, monitor));
        // train3.start();
        // }

    }

    private static void run_train(TrainView view, train_monitor monitor) {
        try {
            Route route = view.loadRoute();
            // inti train
            LinkedList<Segment> train = new LinkedList<Segment>();
            for (int i = 0; i < TRAIN_LENGHT; i++) {
                train.addFirst(route.next());
                monitor.enter_segment(train.peek());
            }

            while (true) {

                Segment next_segment = route.next();
                monitor.enter_segment(next_segment);
                train.addFirst(next_segment);

                train.peekLast().exit();
                Segment ex_seg = monitor.leave_segment(train.peekLast());
                train.remove(ex_seg);
            }
        } catch (InterruptedException e) {
            System.out.println("Yikes!!??");
        }
    }

}
