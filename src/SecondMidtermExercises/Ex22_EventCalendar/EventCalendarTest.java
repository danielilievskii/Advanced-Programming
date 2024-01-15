package SecondMidtermExercises.Ex22_EventCalendar;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

class WrongDateException extends Exception {
    public WrongDateException(Date date) {
        super(String.format("Wrong date: %s", date.toString()));
    }
}

class Event implements Comparable<Event> {
    String name;
    String location;
    Date date;

    public Event(String name, String location, Date date) {
        this.name = name;
        this.location = location;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public int compareTo(Event o) {
        return Comparator.comparing(Event::getDate).thenComparing(Event::getName).compare(this, o);
    }

    @Override
    public String toString() {
        DateFormat df = new SimpleDateFormat("dd MMM, YYY HH:mm");
        return String.format("%s at %s, %s", df.format(date), location, name);
    }
}

class EventCalendar {

    int year;
    Map<Integer, Set<Event>> events;

    public EventCalendar(int year) {
        this.year = year;
        this.events = new HashMap<>();
    }

    static int getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int eventYear = calendar.get(Calendar.YEAR);
        return eventYear;
    }

    static int getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int eventMonth = calendar.get(Calendar.MONTH);
        return eventMonth;
    }

    static int getDayOfYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int eventDay = calendar.get(Calendar.DAY_OF_YEAR);
        return eventDay;
    }
    public void addEvent(String name, String location, Date date) throws WrongDateException {

        if(getYear(date)!=year) {
            throw new WrongDateException(date);
        }
        Event newEvent = new Event(name, location, date);
        events.putIfAbsent(getDayOfYear(date), new TreeSet<>());
        events.get(getDayOfYear(date)).add(newEvent);
    }
    public void listEvents(Date date) {
        if (!events.containsKey(getDayOfYear(date)) || events.get(getDayOfYear(date)).isEmpty()) {
            System.out.println("No events on this day!");
        } else {
            events.get(getDayOfYear(date)).forEach(System.out::println);
        }
    }

    public void listByMonth() {
        Map<Integer, Integer> eventsByMonth = new HashMap<>();
        for(int i=1; i<=12; i++) {
            eventsByMonth.putIfAbsent(i, 0);
        }
        events.values().stream()
                .flatMap(Collection::stream)
                .forEach(event -> {
                    int month = getMonth(event.getDate());
                    eventsByMonth.computeIfPresent(month, (k, v) -> ++v);
                });
        eventsByMonth.entrySet().forEach(entry -> {
            System.out.println(String.format("%d : %d", entry.getKey(), entry.getValue()));
        });

    }
}

public class EventCalendarTest {
    public static void main(String[] args) throws ParseException {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        int year = scanner.nextInt();
        scanner.nextLine();
        EventCalendar eventCalendar = new EventCalendar(year);
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            String name = parts[0];
            String location = parts[1];
            Date date = df.parse(parts[2]);
            try {
                eventCalendar.addEvent(name, location, date);
            } catch (WrongDateException e) {
                System.out.println(e.getMessage());
            }
        }
        Date date = df.parse(scanner.nextLine());
        eventCalendar.listEvents(date);
        eventCalendar.listByMonth();
    }
}

// vashiot kod ovde