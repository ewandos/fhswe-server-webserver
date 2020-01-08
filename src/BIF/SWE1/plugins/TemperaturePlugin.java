package BIF.SWE1.plugins;

import BIF.SWE1.httpUtils.Response;
import BIF.SWE1.httpUtils.ResponseFactory;
import BIF.SWE1.interfaces.IRequest;
import BIF.SWE1.interfaces.IResponse;
import BIF.SWE1.pluginHelper.H2DBService;
import BIF.SWE1.pluginHelper.Sensor;
import BIF.SWE1.pluginSystem.DynamicPlugin;

import java.sql.SQLException;
import java.util.regex.Pattern;

/**
 * Is capable of running a simulated sensor that reads the current temperature
 * and accessing a H2 Database that contains 10.000 temperature data sets
 */
public class TemperaturePlugin extends DynamicPlugin {
    private Sensor sensor;

    /**
     * Creates a new Temperature plugin and starts the sensor thread
     */
    public TemperaturePlugin() {
        identifier = "Temperature";
        sensor = new Sensor();

        // there should be only one Thread active
        // this one thread is created at initialization of this plugin

        Thread th = new Thread(sensor);
        th.setName(identifier);
        th.start();
    }

    /**
     * If a REST Request is made the requested day is searched in the database, if a data set for that
     * day exists, it will be returned. If only the plugin itself is called the current temperature
     * will be returned
     * @param req Http Request
     * @return Http Response containing temperature data as content
     */
    @Override
    public IResponse handle(IRequest req) {
        String restRequest = req.getUrl().getPath();
        IResponse response = new Response();

        final String pattern = "/temperature/[0-9]{4}/[0-9]{2}/[0-9]{2}";
        final Pattern REGEXP = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);

        // The plugin should return specific data after a REST Request or the current temperature that the sensor has read
        if (REGEXP.matcher(restRequest).matches()) {
            try {
                // Get date of REST request and format it to "YYYY-MM-DD"
                String date = restRequest.substring(identifier.length() + 5).replace('/', '-');

                // create SQL Query and execute it on database
                String responseText = H2DBService.select("select * from TEMPERATURE where DATE = '" + date + "';", true);

                // return returned data from database
                response = ResponseFactory.create("text/plain", responseText);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else {
            response = ResponseFactory.create("text/html", "Current temperature: " + sensor.temperature.getValue());
        }

        return response;
    }
}
