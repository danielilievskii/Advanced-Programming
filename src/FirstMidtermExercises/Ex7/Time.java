package FirstMidtermExercises.Ex7;

public class Time implements Comparable<Time> {
    private int hours;
    private int minutes;

    public Time(int hours, int minutes) {
        this.hours = hours;
        this.minutes = minutes;
    }
    void printAMPMformat() {
        String part = "AM";
        int h = hours;
        if (h==0) {
            h+=12;
        }
        else if(h==12) {
            part = "PM";
        } else if(h>=13 && h<=23) {
            part = "PM";
            h-=12;
        }

        System.out.println(String.format("%2d:%02d %s", h, minutes, part));
    }
    void print24format() {
        System.out.println(String.format("%2d:%02d", hours, minutes));
    }

    public Time(String time) throws UnsupportedFormatException, InvalidTimeException {
        String[] parts = time.split("\\.");
        if(parts.length==1) {
            parts = time.split(":");
        }

        if(parts.length==1) {
            throw new UnsupportedFormatException(String.format("UnsupportedFormatException: %s", time));
        }
        this.hours = Integer.parseInt(parts[0]);
        this.minutes = Integer.parseInt(parts[1]);

        if(hours >23 || hours <0 || minutes >59 || minutes<0) {
            throw new InvalidTimeException(String.format("InvalidTimeException: %s", time));
        }
    }

    @Override
    public int compareTo(Time o) {
        if(hours==o.hours) {
            return Integer.compare(minutes, o.minutes);
        } else {
            return Integer.compare(hours, o.hours);
        }
    }
}
