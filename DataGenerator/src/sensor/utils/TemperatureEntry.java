package sensor.utils;

class TemperatureEntry {
    private int id;
    private String date;
    private double max;
    private double min;

    TemperatureEntry(int id, String date, double max, double min) {
        this.id = id;
        this.date = date;
        this.max = max;
        this.min = min;
    }

    String createQuery() {
        StringBuilder builder = new StringBuilder();
        builder.append("INSERT INTO temperature (date, max, min) VALUES('");
        builder.append(date).append("', ");
        builder.append(max).append(", ");
        builder.append(min).append(")");
        return builder.toString();
    }
}
