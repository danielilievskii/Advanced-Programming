package SecondMidtermExercises.Ex15_Airports;
import java.util.*;

class Flight implements Comparable<Flight> {
    String from;
    String to;
    int time;
    int duration;

    public Flight(String from, String to, int time, int duration) {
        this.from = from;
        this.to = to;
        this.time = time;
        this.duration = duration;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public int getTime() {
        return time;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        //14:44-19:16
        int end = time+duration;
        int plus = end/(24 * 60);
        end%= 24*60;

        return String.format("%s-%s %02d:%02d-%02d:%02d%s %dh%02dm", from, to, time/60, time%60, end/60, end%60, plus > 0 ? " +1d" : "", duration/60, duration%60);
    }

    @Override
    public int compareTo(Flight o) {
        return Comparator.comparing(Flight::getTo).thenComparing(Flight::getTime).thenComparing(Flight::getFrom).compare(this, o);
    }
}

class Airport {
    String name;
    String country;
    String code;
    int passengers;
    Map<String, Set<Flight>> flightsTo;

    public Airport(String name, String country, String code, int passengers) {
        this.name = name;
        this.country = country;
        this.code = code;
        this.passengers = passengers;
        this.flightsTo = new TreeMap<>();
    }

    public void addFlightTo(Flight flight) {
        flightsTo.putIfAbsent(flight.getTo(), new TreeSet<>());
        flightsTo.get(flight.getTo()).add(flight);
    }

    @Override
    public String toString() {
        return String.format("%s (%s)\n%s\n%d", name, code, country, passengers);
    }
}

class Airports {

    Map<String, Airport> airports;

    public Airports() {
        this.airports=new TreeMap<>();
    }

    public void addAirport(String name, String country, String code, int passengers) {
        Airport airport = new Airport(name, country, code, passengers);
        airports.putIfAbsent(code, airport);
    }
    public void addFlights(String from, String to, int time, int duration) {
        Flight flight = new Flight(from, to, time, duration);
        airports.get(from).addFlightTo(flight);
    }

    public void showFlightsFromAirport(String code) {
        Airport airport = airports.get(code);
        System.out.println(airport);

        int i=1;
        for(String toCode : airport.flightsTo.keySet()) {
            Set<Flight> flights = airport.flightsTo.get(toCode);
            for (Flight flight : flights) {
                System.out.printf("%d. %s\n", i++, flight);
            }
        }
    }

    public void showDirectFlightsFromTo(String from, String to) {
        Airport airport = airports.get(from);
        if(!airport.flightsTo.containsKey(to)) {
            System.out.printf("No flights from %s to %s\n", from, to);
            return;
        }
        airport.flightsTo.get(to).forEach(System.out::println);
    }

    public void showDirectFlightsTo(String to) {
//        airports.values().stream()
//                .map(airport -> airport.flightsTo.values())
//                .flatMap(Collection::stream)
//                .flatMap(Collection::stream)
//                .filter(flight -> flight.getTo().equals(to))
//                .sorted(Comparator.comparing(Flight::getTime).thenComparing(Flight::getFrom))
//                .forEach(System.out::println);

        Set<Flight> allFlights = new TreeSet<>();
        for(Airport airport : airports.values()) {
            if(airport.flightsTo.containsKey(to)) {
                Set<Flight> flightsTo = airport.flightsTo.get(to);
                allFlights.addAll(flightsTo);
            }
        }
        for (Flight flight : allFlights) {
            System.out.println(flight);
        }
    }

}

public class AirportsTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Airports airports = new Airports();
        int n = scanner.nextInt();
        scanner.nextLine();
        String[] codes = new String[n];
        for (int i = 0; i < n; ++i) {
            String al = scanner.nextLine();
            String[] parts = al.split(";");
            airports.addAirport(parts[0], parts[1], parts[2], Integer.parseInt(parts[3]));
            codes[i] = parts[2];
        }
        int nn = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < nn; ++i) {
            String fl = scanner.nextLine();
            String[] parts = fl.split(";");
            airports.addFlights(parts[0], parts[1], Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));
        }
        int f = scanner.nextInt();
        int t = scanner.nextInt();
        String from = codes[f];
        String to = codes[t];
        System.out.printf("===== FLIGHTS FROM %S =====\n", from);
        airports.showFlightsFromAirport(from);
        System.out.printf("===== DIRECT FLIGHTS FROM %S TO %S =====\n", from, to);
        airports.showDirectFlightsFromTo(from, to);
        t += 5;
        t = t % n;
        to = codes[t];
        System.out.printf("===== DIRECT FLIGHTS TO %S =====\n", to);
        airports.showDirectFlightsTo(to);
    }
}



