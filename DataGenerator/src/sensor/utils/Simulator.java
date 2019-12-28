package sensor.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/*
Per day should be 2 values generated: MIN and MAX:
Over the year the temperature is alternating and the difference between Min & Max is
getting more and less by time.

1. Getting mid-values over the year:
- cos() returns a value between +1 and -1 => range of 2
- mid-values over the year alternate between -5 and 20 => range of 25
- cos() * 12.5 returns a value between -12.5 and +12.5 => range of 25
- cos() * 12.5 + 7.5 returns a value between -5 and 20 => range of 25
- cos(x / 365 * 2 * PI) * 12.5 + 7.5 returns the mid-value of day x of one year
- cos(x-- / 365 * 2 * PI) * 12.5 + 7.5 returns the mid-value of day x of the year
  and goes backwards in time until 10000 values are simulated

2. Getting a bit more random mid-values over the year:
- Math.random() returns value between 0.0 and 1.0
- Math.random() * 2 returns value between 0.0 and 2.0
- Math.random() * 2 - 1 returns value between -1.0 and +1.0

3. Getting a bit random min and max:
- min = mid - (Math.random() * alt + alt)
- max = mid + (Math.random() * alt + alt)

4. Adjust simulation algorithm to reality:
- we simulate the values backwards, beginning with the 31th december 2019 until 10000 values
- x = 10.000 is the 31th december 2019
- statistically the lowest temperature is around 30th january
- [mid] + (30/365 * 2 * Math.PI) adds 30 days => fitted the sin-wave to the year
- x-- % 365 is needed to ensure that the value is never greater than 1
*/

class Simulator {
    static void run() throws IOException {
        // create a file that contains all SQL statements
        FileWriter fileWriter = new FileWriter("./DataGenerator/src/sensor/utils/queries.txt");

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
