package SecondMidtermExercises.Ex10_StopCoronaApp;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import static java.util.Map.Entry.comparingByKey;

interface ILocation{
    double getLongitude();
    double getLatitude();
    LocalDateTime getTimestamp();
}

class UserAlreadyExistException extends Exception {
    public UserAlreadyExistException(String id) {
        //User with id ec008402-5cd6-48d9-9bc7-8c929d34ca6f already exists
        super(String.format("User with id %s already exists", id));
    }
}

class User implements Comparable<User> {
    private String name;
    private String id;
    private List<ILocation> iLocations;
    private LocalDateTime infectedAt;
    boolean infected;
    public User(String name, String id) {
        this.name= name;
        this.id = id;
        iLocations = new ArrayList<>();
        infected = false;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    public String getName() {
        return name;
    }
    public String getId() {
        return id;
    }
    public LocalDateTime getInfectedAt() {
        return infectedAt != null ? infectedAt : LocalDateTime.MAX;
    }
    public void setInfectedAt(LocalDateTime detectedAt) {
        this.infectedAt = detectedAt;
    }
    public boolean isInfected() {
        return infected;
    }
    public void setInfected(boolean infected) {
        this.infected = infected;
    }
    public List<ILocation> getiLocations() {
        return iLocations;
    }
    public void addiLocations(List<ILocation> iLocations) {
        this.iLocations.addAll(iLocations);
    }
    public Map<User, Integer> getDirectContacts(List<User> users) {
        Map<User, Integer> directContacts = new TreeMap<>();
        users.stream().filter(user -> !user.equals(this)).forEach(user -> {
            for(int i=0; i<getiLocations().size(); i++) {
                for(int j=0; j<user.getiLocations().size(); j++) {
                    if(LocationUtils.isDanger(getiLocations().get(i), user.getiLocations().get(j))) {
//                        directContacts.computeIfPresent(user, (k, v) -> ++v);
//                        directContacts.putIfAbsent(user, 1);
                        directContacts.merge(user, 1, Integer::sum);
                    }
                }
            }
        });
        return directContacts;
//        return directContacts.entrySet()
//                .stream()
//                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
//                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, TreeMap::new));
    }
    public Set<User> getIndirectContacts(List<User> users) {
        Set<User> indirectContacts = new TreeSet<>(Comparator.comparing(User::getName).thenComparing(User::getId));
        Set<User> thisUserDirectContacts = getDirectContacts(users).keySet();
        thisUserDirectContacts.stream().forEach(directContact -> {
            Set<User> otherUserDirectContacts = directContact.getDirectContacts(users)
                    .keySet()
                    .stream()
                    .filter(u -> !thisUserDirectContacts.contains(u))
                    .collect(Collectors.toSet());
            indirectContacts.addAll(otherUserDirectContacts);
        });

        return indirectContacts;
    }
    String userComplete() {
        return String.format("%s %s %s", name, id, infectedAt);
    }

    String userHidden() {
        return String.format("%s %s***", name, id.substring(0, 4));
    }

    @Override
    public int compareTo(User o) {
        return Comparator.comparing(User::getId).compare(this, o);
    }
}

class StopCoronaApp {
    Map<String, User> users;

    public StopCoronaApp() {
        this.users = new TreeMap<>();
    }
    public void addUser(String name, String id) throws UserAlreadyExistException {
        if(users.containsKey(id)) {
            throw new UserAlreadyExistException(id);
        }
        users.put(id, new User(name, id));

    }
    public void addLocations (String id, List<ILocation> iLocations) {
        users.get(id).addiLocations(iLocations);
    }
    public void detectNewCase (String id, LocalDateTime timestamp) {
        User infectedUser = users.get(id);
        infectedUser.setInfectedAt(timestamp);
        infectedUser.setInfected(true);
    }

    public Map<User, Integer> getDirectContacts (User u) {
        List<User> allUsers = users.values().stream().collect(Collectors.toList());
        return u.getDirectContacts(allUsers);
    }
    public Collection<User> getIndirectContacts (User u) {
        List<User> allUsers = users.values().stream().filter(user -> !user.equals(u)).collect(Collectors.toList());
        return u.getIndirectContacts(allUsers);
    }
    public void createReport () {
//        Comparator<Map.Entry<User, Integer>> comparatorByKey = Comparator.comparing(Map.Entry::getKey);
//        Comparator<Map.Entry<User, Integer>> comparatorByValue = Comparator.comparing(Map.Entry::getValue);

        List<Integer> directContactsCounts = new ArrayList<>();
        List<Integer> indirectContactsCounts = new ArrayList<>();

        users.values().stream().filter(User::isInfected).sorted(User::compareTo).forEach(user -> {
            System.out.println(String.format("%s", user.userComplete()));
            System.out.println("Direct contacts:");
            getDirectContacts(user).entrySet().stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .forEach(entry -> {
                        System.out.println(String.format("%s %d", entry.getKey().userHidden(), entry.getValue()));
                    });
            int directContacts = getDirectContacts(user).values().stream().mapToInt(Integer::intValue).sum();
            System.out.println(String.format("Count of direct contacts: %d", directContacts));
            directContactsCounts.add(directContacts);


            System.out.println("Indirect contacts:");
            getIndirectContacts(user).stream().forEach(u -> {
                System.out.println(String.format("%s", u.userHidden()));
            });
            int indirectContacts = getIndirectContacts(user).size();
            System.out.println(String.format("Count of indirect contacts: %d", indirectContacts));
            indirectContactsCounts.add(indirectContacts);
        });
        System.out.printf("Average direct contacts: %.4f\n", directContactsCounts.stream().mapToInt(i -> i).average().getAsDouble());
        System.out.printf("Average indirect contacts: %.4f", indirectContactsCounts.stream().mapToInt(i -> i).average().getAsDouble());
    }

}

class LocationUtils {
    public static double distanceBetween(ILocation location1, ILocation location2) {
        return Math.sqrt(Math.pow(location1.getLatitude() - location2.getLatitude(), 2)
                + Math.pow(location1.getLongitude() - location2.getLongitude(), 2));
    }
    public static double timeBetweenInSeconds(ILocation location1, ILocation location2) {
        return Math.abs(Duration.between(location1.getTimestamp(), location2.getTimestamp()).getSeconds());
    }
    public static boolean isDanger(ILocation location1, ILocation location2) {
        return distanceBetween(location1, location2) <= 2.0&&timeBetweenInSeconds(location1, location2) <= 300;
    }

//    public static int dangerContactsBetween(User u1, User u2) {
//        int counter = 0;
//        for (ILocation iLocation : u1.getiLocations()) {
//            for (ILocation iLocation1 : u2.getiLocations()) {
//                if (isDanger(iLocation, iLocation1))
//                    ++counter;
//            }
//        }
//        return counter;
//    }
}

public class StopCoronaTest {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        StopCoronaApp stopCoronaApp = new StopCoronaApp();

        while (sc.hasNext()) {
            String line = sc.nextLine();
            String[] parts = line.split("\\s+");

            switch (parts[0]) {
                case "REG": //register
                    String name = parts[1];
                    String id = parts[2];
                    try {
                        stopCoronaApp.addUser(name, id);
                    } catch (UserAlreadyExistException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "LOC": //add locations
                    id = parts[1];
                    List<ILocation> locations = new ArrayList<>();
                    for (int i = 2; i < parts.length; i += 3) {
                        locations.add(createLocationObject(parts[i], parts[i + 1], parts[i + 2]));
                    }
                    stopCoronaApp.addLocations(id, locations);

                    break;
                case "DET": //detect new cases
                    id = parts[1];
                    LocalDateTime timestamp = LocalDateTime.parse(parts[2]);
                    stopCoronaApp.detectNewCase(id, timestamp);

                    break;
                case "REP": //print report
                    stopCoronaApp.createReport();
                    break;
                default:
                    break;
            }
        }
    }

    private static ILocation createLocationObject(String lon, String lat, String timestamp) {
        return new ILocation() {
            @Override
            public double getLongitude() {
                return Double.parseDouble(lon);
            }

            @Override
            public double getLatitude() {
                return Double.parseDouble(lat);
            }

            @Override
            public LocalDateTime getTimestamp() {
                return LocalDateTime.parse(timestamp);
            }
        };
    }
}
