package SecondMidtermExercises.Ex36_DeliveryApp;

import java.util.*;
class Base {
    String id;
    String name;
    List<Double> orders;
    public Base(String id, String name) {
        this.id = id;
        this.name = name;
        this.orders = new ArrayList<>();
    }
    public String getId() {
        return id;
    }
    public void addOrder(double cost) {
        orders.add(cost);
    }
    public int getOrders() {
        return orders.size();
    }
    public double getTotal() {
        return orders.stream().mapToDouble(i -> i).sum();
    }
    public double getAverage() {
        return orders.stream().mapToDouble(i -> i).average().orElse(0);
    }
    @Override
    public String toString() {
        return String.format("ID: %s Name: %s", id, name);
    }
}

class User extends Base {
    Map<String, Location> userAddresses;
    public User(String id, String name) {
        super(id, name);
        this.userAddresses = new HashMap<>();
    }
    public void addUserAddress(String addressName, Location location) {
        this.userAddresses.put(addressName, location);
    }
    public Location getAddressLocation(String address) {
        return this.userAddresses.get(address);
    }
    @Override
    public String toString() {
        return String.format("ID: %s Name: %s Total orders: %s Total amount spent: %.2f Average amount spent: %.2f", getId(), name, getOrders(), getTotal(), getAverage());
    }
}

class DeliveryPerson extends Base {
    Location currentLocation;
    public DeliveryPerson(String id, String name, Location currentLocation) {
       super(id, name);
        this.currentLocation = currentLocation;
    }
    public void processOrder(int distance) {
        double totalMoneyEarned = (float) (90 + (distance/10) * 10);
        addOrder(totalMoneyEarned);
    }

//    public int compareDistanceToRestaurant(DeliveryPerson other, Location restaurantLocation) {
//        int currentDistance = currentLocation.distance(restaurantLocation);
//        int otherDistance = other.currentLocation.distance(restaurantLocation);
//        if (currentDistance == otherDistance) {
//            return Integer.compare((int) this.getTotal(), (int) other.getTotal());
//        } else return currentDistance - otherDistance;
//    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public void changeCurrentLocation(Location newLocation) {
        this.currentLocation = newLocation;
    }

    @Override
    public String toString() {
        return String.format("ID: %s Name: %s Total deliveries: %s Total delivery fee: %.2f Average delivery fee: %.2f", getId(), name, getOrders(), getTotal(), getAverage());
    }
}
class Restaurant extends Base {
    Location location;
    public Restaurant(String id, String name, Location currentLocation) {
        super(id, name);
        this.location = currentLocation;
    }
    public Location getCurrentLocation() {
        return location;
    }

    @Override
    public String toString() {
        return String.format("ID: %s Name: %s Total orders: %s Total amount earned: %.2f Average amount earned: %.2f", getId(), name, getOrders(), getTotal(), getAverage());
    }
}
class DeliveryApp {
    String name;
    Map<String, User> usersByID;
    Map<String, DeliveryPerson> deliveryPeopleByID;
    Map<String, Restaurant> restaurantsByID;
    public DeliveryApp(String name) {
        this.name = name;
        this.usersByID = new HashMap<>();
        this.deliveryPeopleByID = new HashMap<>();
        this.restaurantsByID = new HashMap<>();
    }
    public void registerDeliveryPerson (String id, String name, Location currentLocation) {
        DeliveryPerson newDeliveryPerson = new DeliveryPerson(id, name, currentLocation);
        this.deliveryPeopleByID.put(id, newDeliveryPerson);
    }
    public void addRestaurant (String id, String name, Location location) {
        Restaurant newRestaurant = new Restaurant(id, name, location);
        this.restaurantsByID.put(id, newRestaurant);
    }
    public void addUser (String id, String name) {
        User newUser = new User(id, name);
        this.usersByID.put(id, newUser);
    }
    public void addAddress (String id, String addressName, Location location) {
        User wantedUser = this.usersByID.get(id);
        wantedUser.addUserAddress(addressName, location);
    }
    public void orderFood(String userId, String userAddressName, String restaurantId, float cost) {
        User user = this.usersByID.get(userId);
        Location userLocation = user.getAddressLocation(userAddressName);
        user.addOrder(cost);

        Restaurant restaurant = this.restaurantsByID.get(restaurantId);
        Location restaurantLocation = restaurant.getCurrentLocation();
        restaurant.addOrder(cost);

        DeliveryPerson deliveryPerson = deliveryPeopleByID.values().stream()
                .min(Comparator.comparing((DeliveryPerson dp) -> dp.currentLocation.distance(restaurantLocation))
                        .thenComparing(DeliveryPerson::getOrders)
                        .thenComparing(DeliveryPerson::getId))
                .orElse(null);

        if(deliveryPerson!=null) {
            int distance = deliveryPerson.getCurrentLocation().distance(restaurantLocation);
            deliveryPerson.changeCurrentLocation(userLocation);
            deliveryPerson.processOrder(distance);
        }
    }
    public void printUsers() {
        this.usersByID.values().stream().sorted(Comparator.comparing(User::getTotal).thenComparing(User::getId).reversed()).forEach(System.out::println);
    }
    public void printRestaurants() {
        this.restaurantsByID.values().stream().sorted(Comparator.comparing(Restaurant::getAverage).thenComparing(Restaurant::getId).reversed()).forEach(System.out::println);
    }
    public void printDeliveryPeople() {
        this.deliveryPeopleByID.values().stream().sorted(Comparator.comparing(DeliveryPerson::getTotal).thenComparing(DeliveryPerson::getId).reversed()).forEach(System.out::println);
    }
}

interface Location {
    int getX();

    int getY();

    default int distance(Location other) {
        int xDiff = Math.abs(getX() - other.getX());
        int yDiff = Math.abs(getY() - other.getY());
        return xDiff + yDiff;
    }
}

class LocationCreator {
    public static Location create(int x, int y) {

        return new Location() {
            @Override
            public int getX() {
                return x;
            }

            @Override
            public int getY() {
                return y;
            }
        };
    }
}

public class DeliveryAppTester {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String appName = sc.nextLine();
        DeliveryApp app = new DeliveryApp(appName);
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] parts = line.split(" ");

            if (parts[0].equals("addUser")) {
                String id = parts[1];
                String name = parts[2];
                app.addUser(id, name);
            } else if (parts[0].equals("registerDeliveryPerson")) {
                String id = parts[1];
                String name = parts[2];
                int x = Integer.parseInt(parts[3]);
                int y = Integer.parseInt(parts[4]);
                app.registerDeliveryPerson(id, name, LocationCreator.create(x, y));
            } else if (parts[0].equals("addRestaurant")) {
                String id = parts[1];
                String name = parts[2];
                int x = Integer.parseInt(parts[3]);
                int y = Integer.parseInt(parts[4]);
                app.addRestaurant(id, name, LocationCreator.create(x, y));
            } else if (parts[0].equals("addAddress")) {
                String id = parts[1];
                String name = parts[2];
                int x = Integer.parseInt(parts[3]);
                int y = Integer.parseInt(parts[4]);
                app.addAddress(id, name, LocationCreator.create(x, y));
            } else if (parts[0].equals("orderFood")) {
                String userId = parts[1];
                String userAddressName = parts[2];
                String restaurantId = parts[3];
                float cost = Float.parseFloat(parts[4]);
                app.orderFood(userId, userAddressName, restaurantId, cost);
            } else if (parts[0].equals("printUsers")) {
                app.printUsers();
            } else if (parts[0].equals("printRestaurants")) {
                app.printRestaurants();
            } else {
                app.printDeliveryPeople();
            }

        }
    }
}
