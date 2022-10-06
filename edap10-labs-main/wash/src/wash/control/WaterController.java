package wash.control;

import actor.ActorThread;
import wash.control.WashingMessage.Order;
import wash.io.WashingIO;

public class WaterController extends ActorThread<WashingMessage> {

    WashingIO io;

    final private int DT = 2;
    private Order mode = Order.WATER_IDLE;
    private WashingMessage last_message;

    public WaterController(WashingIO io) {
        this.io = io;
    }

    @Override
    public void run() {
        try {
            while (true) {
                WashingMessage m = receiveWithTimeout((DT * 1000) / Settings.SPEEDUP);
                if (m != null) {
                    switch (m.getOrder()) {
                        case WATER_IDLE:
                            mode = Order.WATER_IDLE;
                            io.drain(false);
                            io.fill(false);
                            // m.getSender().send(new WashingMessage(this, Order.ACKNOWLEDGMENT));
                            break;
                        case WATER_FILL:
                            mode = Order.WATER_FILL;
                            last_message = m;
                            io.drain(false);
                            io.fill(true);
                            break;
                        case WATER_DRAIN:
                            mode = Order.WATER_DRAIN;
                            last_message = m;
                            io.fill(false);
                            io.drain(true);
                            break;
                        default:
                            throw new Error("We should not be here");
                    }
                }
                switch (mode) {
                    case WATER_FILL:
                        if (io.getWaterLevel() >= 10) {
                            mode = Order.WATER_IDLE;
                            io.fill(false);
                            last_message.getSender().send(new WashingMessage(this, Order.ACKNOWLEDGMENT));
                        }
                        break;

                    case WATER_DRAIN:
                        if (io.getWaterLevel() == 0) {
                            mode = Order.WATER_IDLE;
                            last_message.getSender().send(new WashingMessage(this, Order.ACKNOWLEDGMENT));
                        }
                        break;
                    default:
                        break;

                }

            }
        } catch (InterruptedException unexpected) {
            // we don't expect this thread to be interrupted,
            // so throw an error if it happens anyway
            throw new Error(unexpected);
        }

    }
}
