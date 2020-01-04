package BIF.SWE1.pluginHelper;

public class Temperature {
    private double temperature;
    private int seconds;
    private String date;

    public Temperature (double temperature, int seconds, String date) {
        this.temperature = temperature;
        this.seconds = seconds;
        this.date = date;
    }

    public String createQuery() {
        StringBuilder builder = new StringBuilder();
        builder.append("insert into SENSORDATA (TEMPERATURE, SECONDS, DATE) VALUES(");
        builder.append(temperature).append(", ");
        builder.append(seconds).append(", '");
        builder.append(date).append("')");
        return builder.toString();
    }

    public double getValue() {
        return temperature;
    }

}
