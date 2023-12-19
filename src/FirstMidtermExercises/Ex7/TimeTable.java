package FirstMidtermExercises.Ex7;


import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class TimeTable {
    private List<Time> list;
    public TimeTable() {
        this.list=new ArrayList<>();
    }

    void readTimes(InputStream inputStream) throws InvalidTimeException, UnsupportedFormatException {
        Scanner sc = new Scanner(inputStream);

        while (sc.hasNextLine()) {
            String times = sc.nextLine();

            String[] parts = times.split("\\s+");
            for(String p:parts) {
                Time time = new Time(p);
                list.add(time);
            }
        }
    }

    void writeTimes(OutputStream outputStream, TimeFormat format) {
        PrintWriter pw = new PrintWriter(outputStream);
        //list=list.stream().sorted().collect(Collectors.toList());
        list.sort(Time::compareTo);
        if(format == TimeFormat.FORMAT_24) {
            for(Time t:list) {
                t.print24format();
            }
        } else if(format == TimeFormat.FORMAT_AMPM) {
            for(Time t:list) {
                t.printAMPMformat();
            }
        }

        pw.flush();
    }
}
