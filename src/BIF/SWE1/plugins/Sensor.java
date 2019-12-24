package BIF.SWE1.plugins;

import java.util.Date;

public class Sensor implements Runnable {
    /*
    Temperature should be between 10 and 30 degree, based on time => range of 20
    sin() returns a value between +1 and -1 => a range of 2
    sin() * 10 is a range between +10 and -10 => a range of 20
    sin() * 10 + 20 is a range between 10 and 30 => a range of 20
    ---
    time.getTime() returns the milliseconds till 1970
    milliseconds * 0.001 => returns seconds till 1970
    since the temperature is updated every second * Math.PI => min and max value alternate  every second
    milliseconds * 0.001 * Math.PI / 1440 =>
    */

    @Override
    public void run() {
        while (true) {
            Date time = new Date();
            double seed = time.getTime() * 0.001 * Math.PI / 1440;
            double val = Math.sin(seed);
            val = val * 10 + 20;
            System.out.println(val);
            try {
                Thread.sleep(1000);
            } catch(InterruptedException e) {
                System.out.println(e.getCause());
            }
        }
    }
}
