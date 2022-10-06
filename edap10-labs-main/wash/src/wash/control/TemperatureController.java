package wash.control;

import actor.ActorThread;
import wash.io.WashingIO;
import wash.control.WashingMessage.Order;

public class TemperatureController extends ActorThread<WashingMessage> {

    private WashingIO io;
    private final int DT = 10;
    private int goal_temp;
    private WashingMessage last_message;
    private Boolean heating;

    private Order mode;

    public TemperatureController(WashingIO io) {
        this.io = io;
        mode = Order.TEMP_IDLE;
        heating = false;
        goal_temp = -1;
    }

    @Override
    public void run() {
        try {
            while (true) {
                WashingMessage m = receiveWithTimeout(DT * (1000) / Settings.SPEEDUP);
                if (m != null) {
                    switch (m.getOrder()) {
                        case TEMP_IDLE:
                            mode = Order.TEMP_IDLE;
                            last_message = null;
                            goal_temp = -1;
                            m.getSender().send(new WashingMessage(this, Order.ACKNOWLEDGMENT));
                            break;
                        case TEMP_SET_40:
                            mode = Order.TEMP_SET_40;
                            last_message = m;
                            goal_temp = 40;
                            heating = true;
                            io.heat(true);
                            break;
                        case TEMP_SET_60:
                            mode = Order.TEMP_SET_60;
                            last_message = m;
                            goal_temp = 60;
                            heating = true;
                            io.heat(true);
                            break;
                        default:
                            throw new Error("We shoul not be here");
                    }
                }

                double temp = io.getTemperature();
                switch (mode) {
                    case TEMP_IDLE:
                        double m_u = DT * 0.0478 + 0.2;
                        double m_l = DT * 2.38 * Math.pow(10, -4) * (temp - 20) + 0.2;
                        if (goal_temp == -1) {
                            heating = false;
                            io.heat(false);
                            break;
                        }
                        if (heating && temp > goal_temp - m_u) {
                            heating = false;
                            io.heat(false);
                        } else if (!heating && temp < goal_temp - 2 + m_l) {
                            heating = true;
                            io.heat(true);
                        }
                        break;

                    case TEMP_SET_40:
                        if (temp >= 40 - 2) {
                            mode = Order.TEMP_IDLE;
                            io.heat(false);
                            heating = false;
                            last_message.getSender().send(new WashingMessage(this, Order.ACKNOWLEDGMENT));
                            System.out.println("Temp should now be within 40 margin");
                        }
                        break;

                    case TEMP_SET_60:
                        if (temp >= 60 - 2) {
                            mode = Order.TEMP_IDLE;
                            io.heat(false);
                            heating = false;
                            last_message.getSender().send(new WashingMessage(this, Order.ACKNOWLEDGMENT));
                            System.out.println("Temp should now be within 60 margin");
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
