package SecondMidtermExercises.Ex01;

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
}

class Audition {

    Map<String, Map<String, Participant>> participantsByCity;

    public Audition() {
        participantsByCity = new HashMap<>();
    }
    void addParticpant(String city, String code, String name, int age) {
        Participant p = new Participant(city, code, name, age);

//        if(!participantsByCity.containsKey(city)) {
//            participantsByCity.put(city, new HashMap<>());
//        }
//
//        if(!participantsByCity.get(city).containsKey(code)) {
//            participantsByCity.get(city).put(code, p);
//        }

        participantsByCity.putIfAbsent(city, new HashMap<>());
        participantsByCity.get(city).putIfAbsent(code, p);
    }

    void listByCity(String city){

        Comparator<Participant> comparatorNameThenAgeThenCode = Comparator
                .comparing(Participant::getName)
                .thenComparing(Participant::getAge)
                .thenComparing(Participant::getCode);

        participantsByCity.get(city).values()
                .stream()
                .sorted(comparatorNameThenAgeThenCode)
                .forEach(System.out::println);
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
