package FirstMidtermExercises.Ex21;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Racer implements Comparable<Racer> {
    String name;
    String bestlap;
    int intBestLap;

    public Racer(String line) {
        String[] parts = line.split("\\s+");
        this.name=parts[0];

        String lap1 = parts[1];
        String lap2 = parts[2];
        String lap3 = parts[3];

        int int_lap1 = Integer.parseInt(Arrays.stream(lap1.split(":")).collect(Collectors.joining("")));
        int int_lap2 = Integer.parseInt(Arrays.stream(lap2.split(":")).collect(Collectors.joining("")));
        int int_lap3 = Integer.parseInt(Arrays.stream(lap3.split(":")).collect(Collectors.joining("")));

        if(int_lap1 <= int_lap2 && int_lap1 <= int_lap3) {
            bestlap=lap1;
            intBestLap=int_lap1;
        } else if (int_lap2 <= int_lap1 && int_lap2 <= int_lap3) {
            bestlap=lap2;
            intBestLap=int_lap2;
        } else {
            bestlap=lap3;
            intBestLap=int_lap3;
        }
    }

    @Override
    public String toString() {
        return String.format("%-10s %-10s", name, bestlap);
    }

    @Override
    public int compareTo(Racer o) {
        return Integer.compare(this.intBestLap, o.intBestLap);
    }
}
class F1Race {
    // vashiot kod ovde
    List<Racer> racers;

    public F1Race() {
        this.racers = new ArrayList<>();
    }

    public void readResults(InputStream is) {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        racers = br.lines().map(Racer::new).sorted().collect(Collectors.toList());
    }

    public void printSorted(OutputStream os) {
        PrintWriter pw = new PrintWriter(os);

//        racers.stream().forEach(racer -> {
//            pw.println(racer);
//        });

        IntStream.range(0, racers.size()).forEach(i -> {
            pw.println(String.format("%d. %s", i+1, racers.get(i)));
        });

        pw.flush();
    }

}

public class F1Test {

    public static void main(String[] args) {
        F1Race f1Race = new F1Race();
        f1Race.readResults(System.in);
        f1Race.printSorted(System.out);
    }
}

