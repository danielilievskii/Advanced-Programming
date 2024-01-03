package SecondMidtermExercises.Ex16;

import java.util.*;
import java.util.stream.Collectors;

class BlockContainer<T> {

    private List<Set<T>> elements;
    private int maximum;

    public BlockContainer(int n) {
        this.maximum = n;
        this.elements = new ArrayList<>();
    }

    public void add(T a) {
        if(elements.isEmpty()) {
            Set<T> newSet = new TreeSet<>();
            newSet.add(a);
            elements.add(newSet);
        } else {
            Set<T> lastSet = elements.get(elements.size() - 1);
            if(lastSet.size()<maximum) {
                lastSet.add(a);
            } else {
                Set<T> newSet = new TreeSet<>();
                newSet.add(a);
                elements.add(newSet);
            }
        }
    }

    public boolean remove(T a) {
        if(!elements.isEmpty()) {
            Set<T> lastSet = elements.get(elements.size() - 1);
            if(lastSet.contains(a)) {
                lastSet.remove(a);

                if(lastSet.isEmpty()){
                    elements.remove(lastSet);
                }
                return true;
            }
        }
        return false;
    }

    public void sort() {
        ArrayList<T> allElements = new ArrayList<>();
        allElements = this.elements.stream()
                .flatMap(Collection::stream)
                .sorted()
                .collect(Collectors.toCollection(ArrayList::new));

        this.elements = new ArrayList<Set<T>>();
        allElements.forEach(this::add);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<elements.size(); i++) {
            sb.append(elements.get(i).toString());
            if(i < elements.size()-1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }
}

public class BlockContainerTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int size = scanner.nextInt();
        BlockContainer<Integer> integerBC = new BlockContainer<Integer>(size);
        scanner.nextLine();
        Integer lastInteger = null;
        for(int i = 0; i < n; ++i) {
            int element = scanner.nextInt();
            lastInteger = element;
            integerBC.add(element);
        }
        System.out.println("+++++ Integer Block Container +++++");
        System.out.println(integerBC);
        System.out.println("+++++ Removing element +++++");
        integerBC.remove(lastInteger);
        System.out.println("+++++ Sorting container +++++");
        integerBC.sort();
        System.out.println(integerBC);
        BlockContainer<String> stringBC = new BlockContainer<String>(size);
        String lastString = null;
        for(int i = 0; i < n; ++i) {
            String element = scanner.next();
            lastString = element;
            stringBC.add(element);
        }
        System.out.println("+++++ String Block Container +++++");
        System.out.println(stringBC);
        System.out.println("+++++ Removing element +++++");
        stringBC.remove(lastString);
        System.out.println("+++++ Sorting container +++++");
        stringBC.sort();
        System.out.println(stringBC);
    }
}




