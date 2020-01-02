package BIF.SWE1.pluginHelper;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;

import BIF.SWE1.pluginHelper.H2DBService;

public class Sensor implements Runnable {
    /*
    Temperature should be between 10 and 30 degree, based on time => range of 20
    sin() returns a value between +1 and -1 => a range of 2
    sin() * 5 is a range between +5 and -5 => a range of 10
    */

    @Override
    public void run() {
        try {
            H2DBService database = new H2DBService();

            while (true) {
                LocalDateTime date = LocalDateTime.now();

                float seconds = date.toLocalTime().toSecondOfDay();
                double seed = (seconds / 86400) * 2 * Math.PI;
                double val = Math.sin(seed);

                // add a bit randomness to simulate "real" data
                double rand = Math.random() * 0.5 - 0.5;
                val = val * 10 + rand;

                // System.out.println(val);
                // System.out.println(date.toString());

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formatDateTime = date.format(formatter);

                Temperature temperature = new Temperature(val, (int) seconds, formatDateTime);
                database.insert(temperature.createQuery());
                Thread.sleep(5000);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch(InterruptedException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }
}
