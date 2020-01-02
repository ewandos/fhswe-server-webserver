package BIF.SWE1.pluginHelper;

import java.sql.*;

/*
    To setup a H2Database in an Intellij IDEA Project:
    First create an database through the "Data Source from URL". Relative paths point to the
    Intellij IDEA directory NOT the working directory. Because of that you need to go into the
    "Data Source Properties" and change the path to the absolute path of the  working directory.
    After the first SQL-Statement in the console the files for the H2 Database are created.
    Now you can connect to them in your code. The absolute path is only for set up the
    database, so it doesn't matter that it is an absolute path. In the code itself it refers
    to the relative path in the working directory.

    The hardest error was that the driver of the database-tool of Intellij is independent
    to the library that is imported in the module. The versions were different, so I could not
    either connect to a database that was created by the database-tool with the library in my
    code and other way round.
 */

public class H2DBService {
    private Connection conn;

    H2DBService() throws SQLException {
        // connects to local embedded H2 Database using the included library from project settings
        this.conn = DriverManager.getConnection("jdbc:h2:./database/sensor", "","" );
    }

    public void insert(String query) throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.executeUpdate(query);
    }

    public String select(String query) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        StringBuilder builder = new StringBuilder();

        ResultSetMetaData rsMetaData = rs.getMetaData();
        int columnsNumber = rsMetaData.getColumnCount();
        while (rs.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
                if (i > 1) System.out.print(",  ");
                String columnValue = rs.getString(i);
                builder.append(columnValue).append(" ").append(rsMetaData.getColumnName(i));
            }
            builder.append("\n");
        }

        return builder.toString();
    }
}

