package GENERATOR;

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

import java.io.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Main {
    public static void main(String[] args) {

        try {
            // simulate 10.000 temperature entries as proper SQL INSERT statements
            Simulator.run();

            // create a service object for the H2 Database
            H2DBService database = new H2DBService();

            // load file that contains all queries
            String file = "./DataGenerator/src/GENERATOR/queries.txt";
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String query;

                // read all entries of the file
                while ((query = br.readLine()) != null) {
                    // execute query into database
                    database.insert(query);
                }
            }
        } catch(SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        } catch(FileNotFoundException e) {
            System.out.println("File not found!");
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }

        /*
        try {
            generateValues();
        } catch (IOException e) {
            System.out.println("File not found!");
        } catch (InterruptedException e) {
            System.out.println("Sleep error");
        }

         */
    }
}
