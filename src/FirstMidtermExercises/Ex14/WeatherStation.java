package FirstMidtermExercises.Ex14;

import java.util.*;
import java.util.stream.Collectors;

public class WeatherStation {
    int days;
    List<Measurment> measurments;
    public static final long MS = 86400000;

    public WeatherStation(int days) {
        this.days = days;
        this.measurments=new ArrayList<>();
    }

    public void addMeasurment(float temperature, float wind, float humidity, float visibility, Date date) {
        Measurment m = new Measurment(temperature, wind, humidity, visibility, date);

        for(Measurment mes : measurments) {
            if(mes.compareTo(m) == 0) {
                return;
            }
        }

        measurments.removeIf(i -> m.getDate().getTime() - i.getDate().getTime() > days * MS);

        measurments.add(m);

    }

    public int total() {
        return measurments.size();
    }

    public void status(Date from, Date to) {
        List<Measurment> filteredMeasurments;

        filteredMeasurments = this.measurments.stream().filter(i -> i.getDate().getTime() >= from.getTime() && i.getDate().getTime() <= to.getTime()).sorted().collect(Collectors.toList());

        if(filteredMeasurments.isEmpty()) {
            throw new RuntimeException();
        }

        double average = filteredMeasurments.stream().mapToDouble(Measurment::getTemperature).average().getAsDouble();
        for(Measurment m : filteredMeasurments) {
            System.out.println(m.toString().replace("UTC" , "GMT"));
        }
        System.out.println(String.format("Average temperature: %.2f", average));
    }
}
