package BIF.SWE1.pluginHelper;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

/**
 * A single Runnable that simulates a temperature sensor.
 * Every 5 seconds the Sensor sends the "current" temperature
 * to a H2 Database
 */
public class Sensor implements Runnable {
    /*
    Temperature should be between 10 and 30 degree, based on time => range of 20
    sin() returns a value between +1 and -1 => a range of 2
    sin() * 5 is a range between +5 and -5 => a range of 10
    */

    public Temperature temperature;

    /**
     * Uses a sin() function to simulate the output data of a sensor that
     * tracks the current temperature. Every 5 seconds a new temperature is send
     * to the H2 Database by using a H2DBService
     */
    @Override
    public void run() {
        try {
            while (true) {
                LocalDateTime date = LocalDateTime.now();

                float seconds = date.toLocalTime().toSecondOfDay();
                double seed = (seconds / 86400) * 2 * Math.PI;
                double val = Math.sin(seed);

                // add a bit randomness to simulate "real" data
                double rand = Math.random() * 0.5 - 0.5;
                val = val * 10 + rand;

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formatDateTime = date.format(formatter);

                // System.out.println(val + " : " + formatDateTime);

                temperature = new Temperature(val, (int) seconds, formatDateTime);
                H2DBService.insert(temperature.createQuery());
                Thread.sleep(5000);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch(InterruptedException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }


}
