package SecondMidtermExercises.Ex1_NotWorking;

import java.util.*;

class Participant {
    private String city;
    private String code;
    private String name;
    private int age;

    public Participant(String city, String code, String name, int age) {
        this.city = city;
        this.code = code;
        this.name = name;
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return String.format("%s %s %d", getCode(), getName(), getAge());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Participant that = (Participant) o;
        return Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}

class Audition {

    Map<String, TreeSet<Participant>> participantsByCity;

    public Audition() {
        participantsByCity = new HashMap<>();
    }
    void addParticpant(String city, String code, String name, int age) {
        Participant p = new Participant(city, code, name, age);

        participantsByCity.putIfAbsent(city, new TreeSet<>(Comparator.comparing(Participant::getName).thenComparing(Participant::getAge).thenComparing(Participant::getCode)));

        if(!participantsByCity.get(city).contains(p)) {
            participantsByCity.get(city).add(p);
        }
    }

    void listByCity(String city){
        participantsByCity.get(city).forEach(System.out::println);
    }

}

public class AuditionTest {
    public static void main(String[] args) {
        Audition audition = new Audition();
        List<String> cities = new ArrayList<String>();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            if (parts.length > 1) {
                audition.addParticpant(parts[0], parts[1], parts[2],
                        Integer.parseInt(parts[3]));
            } else {
                cities.add(line);
            }
        }
        for (String city : cities) {
            System.out.printf("+++++ %s +++++\n", city);
            audition.listByCity(city);
        }
        scanner.close();
    }
}
