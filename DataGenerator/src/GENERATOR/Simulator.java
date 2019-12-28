package GENERATOR;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Simulator {
    public static void run() throws IOException {
        // create a file that contains all SQL statements
        FileWriter fileWriter = new FileWriter("./DataGenerator/src/GENERATOR/queries.txt");

        // 10.000 values need to be simulated
        double x = 10000;
        int count = 0;

        // the temperature should alternate between x * 2 and x * 4
        double alt = 3.5f; // 7 and 14

        // set calendar to beginning of simulating the data
        Calendar calendar = Calendar.getInstance();
        calendar.set(2019, Calendar.DECEMBER, 31);

        // until 10.000 values are simulated
        while (count < 10050) {
            count++;
            // get random value for alternating the mid-value
            double rand = Math.random() * 2f - 1f;

            // get a mid value from a sin-wave that goes from -5 to 20
            double mid = Math.cos(((x-- % 365f) / 365f) * 2 * Math.PI) * 12.5f + 7.5f + rand;

            // the sin-wave matches with the 30th day of the year
            double mid_fit = mid + (30f / 365f * 2 * Math.PI);

            // get Min and Max Value by mul the mid-value
            double min = mid_fit - (Math.random() * alt + alt);
            double max = mid_fit + (Math.random() * alt + alt);

            // subtract one day to go backwards in time
            calendar.add(Calendar.DAY_OF_MONTH, - 1);

            // format the date to proper format for SQL statement
            SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd");
            Date d = calendar.getTime();
            String date = dtf.format(d);

            // create new object to get the sql query
            TemperatureEntry te = new TemperatureEntry(count, date, max, min);

            // write the query into the file
            fileWriter.write(te.createQuery() + "\n");

            /*
            System.out.println(count + ":");
            System.out.println(dtf.format(d));
            System.out.println("Min: " + min);
            System.out.println("Max: " + max);
            Thread.sleep(500);
            */
        }

        fileWriter.close();
    }
}
