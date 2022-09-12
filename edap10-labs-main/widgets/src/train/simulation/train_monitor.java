package train.simulation;

import train.model.Segment;
import java.util.HashSet;

public class train_monitor {
    private HashSet<Segment> segment_set;

    public train_monitor() {
        this.segment_set = new HashSet<Segment>();
    }

    public synchronized void enter_segment(Segment next_segment) throws InterruptedException {

        while (!segment_set.add(next_segment)) {
            // System.out.println("Waiting with " + Thread.currentThread().getId());
            wait();
            // System.out.println("Done Waiting " + Thread.currentThread().getId());
        }
        next_segment.enter();
        // System.out.println(Thread.currentThread().getId() + " entered segment");
    }

    public synchronized Segment leave_segment(Segment tail_segment) throws InterruptedException {
        segment_set.remove(tail_segment);
        notifyAll();
        // System.out.println(Thread.currentThread().getId() + " exited segment");
        return tail_segment;
    }
}