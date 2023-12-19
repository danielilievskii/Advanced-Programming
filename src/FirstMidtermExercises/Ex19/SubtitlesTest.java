package FirstMidtermExercises.Ex19;

import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

public class SubtitlesTest {
    public static void main(String[] args) {
        Subtitles subtitles = new Subtitles();
        int n = subtitles.loadSubtitles(System.in);
        System.out.println("+++++ ORIGINIAL SUBTITLES +++++");
        subtitles.print();
        int shift = n * 37;
        shift = (shift % 2 == 1) ? -shift : shift;
        System.out.println(String.format("SHIFT FOR %d ms", shift));
        subtitles.shift(shift);
        System.out.println("+++++ SHIFTED SUBTITLES +++++");
        subtitles.print();
    }
}

class Element {
    public int timeFrom;
    public int timeTo;
    public int number;
    String text;

    public Element(int number, String time, String text) {
        this.number = number;
        this.text = text;

        String[] parts = time.split(" ");
        timeFrom = stringToTime(parts[0]);
        timeTo = stringToTime(parts[2]);
    }

    static int stringToTime(String time) {
        String[] parts = time.split(",");
        int resMS = Integer.parseInt(parts[1]);

        String[] timeParts = parts[0].split(":");
        int hours = Integer.parseInt(timeParts[0]);
        int minutes = Integer.parseInt(timeParts[1]);
        int seconds = Integer.parseInt(timeParts[2]);

        resMS+= seconds * 1000;
        resMS+= minutes * 60 * 1000;
        resMS+= hours * 60 * 60 * 1000;
        return resMS;
    }

    static String timeToString(int time) {
        int hours = time / (60 * 60 * 1000);
        time= time % (60 * 60 * 1000);
        int minutes = time / (60 * 1000);
        time = time % (60 * 1000);
        int seconds = time / 1000;
        int ms = time % 1000;

        return String.format("%02d:%02d:%02d,%03d", hours,minutes,seconds,ms);
    }

    public void shift(int ms) {
        timeFrom+=ms;
        timeTo+=ms;
    }

    @Override
    public String toString() {
        return String.format("%d\n%s --> %s\n%s", number, timeToString(timeFrom), timeToString(timeTo), text);
    }
}

class Subtitles {
    List<Element> elements;

    public Subtitles() {
        this.elements=new ArrayList<>();
    }

    int loadSubtitles(InputStream inputStream) {
        Scanner sc = new Scanner(inputStream);

        while (sc.hasNextLine()) {
            int number = Integer.parseInt(sc.nextLine());
            String time = sc.nextLine();
            StringBuilder text = new StringBuilder();
            while (true) {
                if(!sc.hasNextLine()) break;

                String line = sc.nextLine();
                if (line.trim().length()==0)
                    break;

                text.append(line);
                text.append("\n");
            }
            Element element = new Element(number, time, text.toString());
            elements.add(element);
        }
        return elements.size();
    }

    void shift(int ms) {
        for(Element el: elements) {
            el.shift(ms);
        }
    }

    void print() {
        elements.stream().forEach(elem -> System.out.println(elem));
    }

}
