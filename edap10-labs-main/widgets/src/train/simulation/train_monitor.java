package train.simulation;

import train.model.Segment;
import java.util.HashSet;

public class train_monitor{
    private HashSet<Segment> segment_set;

    public train_monitor() {
        this.segment_set = new HashSet<Segment>();
    }

    public synchronized void enter_segment(){

    }
}