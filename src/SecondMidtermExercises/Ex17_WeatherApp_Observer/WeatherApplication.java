package SecondMidtermExercises.Ex17_WeatherApp_Observer;

import java.util.*;

interface Observer {
    public void update(float temp, float humidity, float pressure);
    public void display();
}
interface Subject {
    void register(Observer other);
    void remove(Observer other);
    void notifyObservers();
}

interface Displayable {
    void display();
}

class CurrentConditionsDisplay implements Observer, Displayable {
    private float temperature;
    private float humidity;
    private Subject weatherStation;

    public CurrentConditionsDisplay(Subject weatherStation) {
        this.weatherStation = weatherStation;
        weatherStation.register(this);
    }
    @Override
    public void update(float temp, float humidity, float pressure) {
        this.temperature = temp;
        this.humidity = humidity;
        display();
    }
    public void display() {
        System.out.println("Temperature: " + temperature + "F");
        System.out.println("Humidity: " + humidity + "%");
    }
}

class ForecastDisplay implements Observer, Displayable {
    private float currentPressure = 0.0f;
    private float lastPressure;
    private Subject weatherStation;

    public ForecastDisplay(Subject weatherStation) {
        this.weatherStation = weatherStation;
        weatherStation.register(this);
    }
    @Override
    public void update(float temp, float humidity, float pressure) {
        this.lastPressure = currentPressure;
        this.currentPressure = pressure;
        display();
    }
    public void display() {
        System.out.print("Forecast: ");
        if (currentPressure > lastPressure) {
            System.out.println("Improving");
        } else if (currentPressure == lastPressure) {
            System.out.println("Same");
        } else if (currentPressure < lastPressure) {
            System.out.println("Cooler");
        }
    }
}

class WeatherDispatcher implements Subject {
    Set<Observer> observers;
    private float temperature;
    private float humidity;
    private float pressure;
    public WeatherDispatcher() {
        this.observers = new HashSet<>();
    }

    public void setMeasurements(float temperature, float humidity, float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        notifyObservers();
    }
    @Override
    public void register(Observer other) {
        observers.add(other);
    }

    @Override
    public void remove(Observer other) {
        observers.remove(other);
    }
    @Override
    public void notifyObservers() {
        for(Observer observer : observers) {
            observer.update(temperature, humidity, pressure);
        }
    }
}

public class WeatherApplication {

    public static void main(String[] args) {
        WeatherDispatcher weatherDispatcher = new WeatherDispatcher();

        CurrentConditionsDisplay currentConditions = new CurrentConditionsDisplay(weatherDispatcher);
        ForecastDisplay forecastDisplay = new ForecastDisplay(weatherDispatcher);

        Scanner scanner = new Scanner(System.in);
        boolean error = false;
        while (scanner.hasNext()) {
            if (error) {
                System.out.println();
            }
            String line = scanner.nextLine();
            String[] parts = line.split("\\s+");
            weatherDispatcher.setMeasurements(Float.parseFloat(parts[0]), Float.parseFloat(parts[1]), Float.parseFloat(parts[2]));
            if(parts.length > 3) {
                int operation = Integer.parseInt(parts[3]);
                if(operation==1) {
                    weatherDispatcher.remove(forecastDisplay);
                }
                if(operation==2) {
                    weatherDispatcher.remove(currentConditions);
                }
                if(operation==3) {
                    weatherDispatcher.register(forecastDisplay);
                }
                if(operation==4) {
                    weatherDispatcher.register(currentConditions);
                }

            }
            error = true;
        }
    }
}