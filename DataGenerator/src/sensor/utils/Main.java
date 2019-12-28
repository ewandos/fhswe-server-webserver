package sensor.utils;

import java.io.*;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {

        /*
         This algorithm first simulates 10.000 temperature entries (min- & max temperature) of 5000 days,
         writes them into a file called "queries.txt" as prepared SQL Statements and executes them on a
         H2 Database which is embedded in the local working directory.
         */

        try {
            // simulate 10.000 temperature entries as proper SQL INSERT statements
            Simulator.run();

            // create a service object for the connection to the H2 Database
            H2DBService database = new H2DBService();

            // load file that contains all queries
            String file = "./DataGenerator/src/sensor/utils/queries.txt";
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String query;

                // read all entries of the file
                while ((query = br.readLine()) != null) {
                    // execute query into database
                    System.out.println(query);
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
    }
}
