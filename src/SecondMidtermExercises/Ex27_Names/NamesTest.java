package SecondMidtermExercises.Ex27_Names;

import java.util.*;
import java.util.stream.Collectors;

class Names {

    Map<String, Integer> namesMap;

    public Names() {
        this.namesMap = new TreeMap<>();
    }

    public void addName(String name) {
        this.namesMap.computeIfPresent(name, (k, v) -> ++v);
        this.namesMap.putIfAbsent(name, 1);
    }
    public void printN(int n) {
        this.namesMap.entrySet().stream()
                .filter(entry -> entry.getValue() >= n)
                .forEach(entry -> System.out.println(String.format("%s (%d) %d", entry.getKey(), entry.getValue(), calcUniqueChars(entry.getKey()))));
    }
    public String findName(int len, int x) {
        List<String> names = namesMap.keySet()
                .stream()
                .filter(name -> name.length() < len)
                .sorted()
                .collect(Collectors.toList());

        return names.get(x % names.size());
    }

    public static int calcUniqueChars(String name) {
        List<Character> uniqueChars = new ArrayList<>();
        for(Character c : name.toLowerCase().toCharArray()) {
            if(!uniqueChars.contains(c)) {
                uniqueChars.add(c);
            }
        }
        return uniqueChars.size();
    }
}

public class NamesTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        Names names = new Names();
        for (int i = 0; i < n; ++i) {
            String name = scanner.nextLine();
            names.addName(name);
        }
        n = scanner.nextInt();
        System.out.printf("===== PRINT NAMES APPEARING AT LEAST %d TIMES =====\n", n);
        names.printN(n);
        System.out.println("===== FIND NAME =====");
        int len = scanner.nextInt();
        int index = scanner.nextInt();
        System.out.println(names.findName(len, index));
        scanner.close();

    }
}

// vashiot kod ovde
