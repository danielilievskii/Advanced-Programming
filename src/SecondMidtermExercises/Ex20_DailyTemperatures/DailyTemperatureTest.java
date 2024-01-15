package SecondMidtermExercises.Ex20_DailyTemperatures;

import java.io.*;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * I partial exam 2016
 */

class DailyTemperature {
    int day;
    List<Double> temperatures;
    DoubleSummaryStatistics doubleSummaryStatistics;

    public DailyTemperature(int day, List<Double> temperatures) {
        this.day = day;
        this.temperatures = temperatures;
        doubleSummaryStatistics = temperatures.stream().collect(Collectors.summarizingDouble(x->x));
        //doubleSummaryStatistics = temperatures.stream().mapToDouble(i->i).summaryStatistics();
    }
    public int getDay() {
        return day;
    }
    static double toCelsius(double fahrenheit) {
        return (fahrenheit - 32) * 5 / 9;
    }
    static double toFahrenheit(double celsius) {
        return (celsius * 9) / 5 + 32;
    }
    public String summary(char c) {
        double min = doubleSummaryStatistics.getMin();
        double max = doubleSummaryStatistics.getMax();
        double average = doubleSummaryStatistics.getAverage();
        int count = (int) doubleSummaryStatistics.getCount();

        if(c == 'F') { //initially stored as Celsius
            min = toFahrenheit(min);
            max = toFahrenheit(max);
            average = toFahrenheit(average);
        }
        return String.format("Count: %3d Min: %6.2f%c Max: %6.2f%c Avg: %6.2f%c",
                doubleSummaryStatistics.getCount(),
                min, c,
                max, c,
                average, c);

    }
}

class DailyTemperatureFactory {
    public static DailyTemperature createDailyTemperature(String line) {
        String[] parts = line.split("\\s+");

        int day = Integer.parseInt(parts[0]);
        List<Double> temps = IntStream.range(1, parts.length)
                .mapToObj(i -> tempFromString(parts[i]))
                .collect(Collectors.toList());

//        List<Double> temperatures = Arrays.stream(parts).skip(1)
//                .map(DailyTemperatureFactory::tempFromString)
//                .collect(Collectors.toList());
        return new DailyTemperature(day, temps);
    }

    public static double tempFromString(String temp) {
        double doubleTemp = Double.parseDouble(temp.substring(0, temp.length() -1));
        if(temp.endsWith("C")) {
            return doubleTemp;
        } else {
            return DailyTemperature.toCelsius(doubleTemp);
        }
    }
}
class DailyTemperatures {
    List<DailyTemperature> temperatures;

    void readTemperatures(InputStream inputStream) {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        temperatures = br.lines()
                .map(DailyTemperatureFactory::createDailyTemperature)
                .collect(Collectors.toList());
    }
    void writeDailyStats(OutputStream outputStream, char scale) {
        PrintWriter pw = new PrintWriter(outputStream);
        temperatures.stream()
                .sorted(Comparator.comparing(DailyTemperature::getDay))
                .forEach(dailyTemp -> pw.println(String.format("%3d: %s", dailyTemp.getDay(), dailyTemp.summary(scale))));

        pw.flush();
    }
}

public class DailyTemperatureTest {
    public static void main(String[] args) {
        DailyTemperatures dailyTemperatures = new DailyTemperatures();
        dailyTemperatures.readTemperatures(System.in);
        System.out.println("=== Daily temperatures in Celsius (C) ===");
        dailyTemperatures.writeDailyStats(System.out, 'C');
        System.out.println("=== Daily temperatures in Fahrenheit (F) ===");
        dailyTemperatures.writeDailyStats(System.out, 'F');
    }
}

