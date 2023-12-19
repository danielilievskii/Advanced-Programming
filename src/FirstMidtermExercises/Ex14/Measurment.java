package FirstMidtermExercises.Ex14;

import java.util.Date;

public class Measurment implements Comparable<Measurment> {
    float temperature;
    float wind;
    float humidity;
    float visibility;
    Date date;

    public Measurment(float temperature, float wind, float humidity, float visibility, Date date) {
        this.temperature = temperature;
        this.wind = wind;
        this.humidity = humidity;
        this.visibility = visibility;
        this.date = date;
    }

    @Override
    public int compareTo(Measurment o) {
        if(Math.abs(date.getTime() - o.date.getTime()) < 150000) {
            return 0;
        } else {
            return date.compareTo(o.date);
        }
    }

    public float getTemperature() {
        return temperature;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        return String.format("%f %f km/h %f%% %f km %s", temperature, wind, humidity, visibility, date.toString());
    }
}
