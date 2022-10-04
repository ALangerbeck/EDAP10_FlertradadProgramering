package wash.control;

import actor.ActorThread;
import wash.control.WashingMessage.Order;
import wash.io.WashingIO;

public class SpinController extends ActorThread<WashingMessage> {

    WashingIO io;
    WashingMessage.Order mode = Order.SPIN_OFF;
    boolean spinning_right;

    public SpinController(WashingIO io) {
        this.io = io;
    }

    @Override
    public void run() {
        try {
            while (true) {
                // wait for up to a (simulated) minute for a WashingMessage
                WashingMessage m = receiveWithTimeout(60000 / Settings.SPEEDUP);

                // if m is null, it means a minute passed and no message was received
                if (m != null) {
                    System.out.println("got " + m);
                    switch (m.getOrder()) {
                        case SPIN_SLOW:
                            mode = Order.SPIN_SLOW;
                            spinning_right = false;
                            io.setSpinMode(io.SPIN_LEFT);
                            m.getSender().send(new WashingMessage(this, Order.ACKNOWLEDGMENT));
                            break;
                        case SPIN_FAST:
                            mode = Order.SPIN_FAST;
                            io.setSpinMode(io.SPIN_FAST);
                            m.getSender().send(new WashingMessage(this, Order.ACKNOWLEDGMENT));
                            break;
                        case SPIN_OFF:
                            mode = Order.SPIN_OFF;
                            io.setSpinMode(io.SPIN_IDLE);
                            m.getSender().send(new WashingMessage(this, Order.ACKNOWLEDGMENT));
                        default:
                            break;
                    }
                }

                // The Mode behaviour
                switch (mode) {
                    case SPIN_SLOW:
                        if (spinning_right) {
                            io.setSpinMode(io.SPIN_RIGHT);
                            spinning_right = !spinning_right;
                            break;
                        } else {
                            io.setSpinMode(io.SPIN_LEFT);
                            spinning_right = !spinning_right;
                            break;
                        }
                    case SPIN_FAST:
                        break;

                    case SPIN_OFF:
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
