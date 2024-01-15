package SecondMidtermExercises.Ex21_PhoneBook;

import java.util.*;


class DuplicateNumberException extends Exception {
    public DuplicateNumberException(String msg) {
        super(msg);
    }
}

class Contact {
    String name;
    String number;

    static Comparator<Contact> comparatorByNameThenNumber = Comparator.comparing(Contact::getName).thenComparing(Contact::getNumber);

    public Contact(String name, String number) {
        this.name = name;
        this.number = number;
    }

    @Override
    public String toString() {
        return String.format("%s %s", name, number);
    }

    public String getName() {
        return name;
    }

    public List<String> getPhoneKeys() {
        List<String> keys = new ArrayList<String>();
        int len = number.length();
        for (int i = 0; i <= len - 3; ++i) {
            for (int j = i + 3; j <= len; ++j) {
                String key = number.substring(i, j);
                keys.add(key);
            }
        }
        return keys;
    }

    public String getNumber() {
        return number;
    }
}
class PhoneBook {

    private Set<String> uniquePhoneNumbers;
    private Map<String, Set<Contact>> nameAndContactsMap;
    private Map <String, Set<Contact>> numberAndContactsMap;


    public PhoneBook() {
        this.uniquePhoneNumbers = new HashSet<>();
        this.nameAndContactsMap = new HashMap<>();
        this.numberAndContactsMap = new TreeMap<>();
    }


    void addContact(String name, String number) throws DuplicateNumberException {
        Contact contact = new Contact(name, number);
        if(uniquePhoneNumbers.contains(number)) {
            throw new DuplicateNumberException(String.format("Duplicate number: %s", number));
        } else {
            uniquePhoneNumbers.add(number);
        }
        nameAndContactsMap.putIfAbsent(name, new TreeSet<>(Contact.comparatorByNameThenNumber));
        nameAndContactsMap.get(name).add(contact);

        //for contactsByNumber method - complexity O(log N) :)
        List<String> keys = contact.getPhoneKeys();
        for(String key : keys) {
            numberAndContactsMap.putIfAbsent(key, new TreeSet<>(Contact.comparatorByNameThenNumber));
            numberAndContactsMap.get(key).add(contact);
        }
    }

    public void contactsByNumber(String number) {
        //SOLUTION 1 - complexity O(log(N))
        if(!numberAndContactsMap.containsKey(number)) {
            System.out.println("NOT FOUND");
            return;
        }
        numberAndContactsMap.get(number).forEach(System.out::println);


        //SOLUTION 2 - complexity O(N*log(N))
//        List<Contact> contactsByNum = nameAndContactsMap.values().stream()
//                .flatMap(Collection::stream)
//                .filter(contact -> contact.getNumber().contains(number))
//                .sorted(Contact.comparatorByNameThenNumber)
//                .collect(Collectors.toList());
//
//        if(contactsByNum.isEmpty()) {
//            System.out.println("NOT FOUND");
//        } else {
//            contactsByNum.forEach(System.out::println);
//        }
    }
    public void contactsByName(String name) {
        if(!nameAndContactsMap.containsKey(name)) {
            System.out.println("NOT FOUND");
        } else {
            nameAndContactsMap.get(name).forEach(System.out::println);
            //FOR NULL POINTER EXCEPTION, IN THIS CASE WE HAVE IF STATEMENT
            //contactsByNameMap.getOrDefault(name, new TreeSet<>(Contact.comparatorByNameThenNumber)).forEach(System.out::println);
        }
    }
}

public class PhoneBookTest {

    public static void main(String[] args) {
        PhoneBook phoneBook = new PhoneBook();
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(":");
            try {
                phoneBook.addContact(parts[0], parts[1]);
            } catch (DuplicateNumberException e) {
                System.out.println(e.getMessage());
            }
        }
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            System.out.println(line);
            String[] parts = line.split(":");
            if (parts[0].equals("NUM")) {
                phoneBook.contactsByNumber(parts[1]);
            } else {
                phoneBook.contactsByName(parts[1]);
            }
        }
    }

}

// Вашиот код овде


