package train.simulation;

import java.util.LinkedList;

import javax.swing.text.View;

import train.model.Route;
import train.model.Segment;
import train.view.TrainView;


public class TrainSimulation {
    static int TRAIN_LENGHT = 3;
    public static void main(String[] args) {

        TrainView view = new TrainView();

        // for (int i = 0; i<3;i++){
        Thread train1 = new Thread(() -> run_train(view));
        train1.start();
        Thread train2 = new Thread(() -> run_train(view));
        train2.start();
        Thread train3 = new Thread(() -> run_train(view));
        train3.start();
        // }

    }

    private static void run_train(TrainView view) {
        Route route = view.loadRoute();
        // inti train
        LinkedList<Segment> train1 = new LinkedList<Segment>();
        for (int i = 0; i < TRAIN_LENGHT; i++) {
            train1.addFirst(route.next());
            train1.peek().enter();
        }

        while (true) {
            Segment next_segment = route.next();
            next_segment.enter();
            train1.addFirst(next_segment);
            Segment exit_segment = train1.removeLast();
            exit_segment.exit();

        }

    }

}
