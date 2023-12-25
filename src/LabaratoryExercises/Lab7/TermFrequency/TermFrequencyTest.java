package LabaratoryExercises.Lab7.TermFrequency;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

class TermFrequency {
    private final Map<String, Integer> map;
    private int count;
    public TermFrequency(InputStream inputStream, String[] stopWords) {
        Scanner scanner = new Scanner(inputStream);
        this.map = new HashMap<>();
        this.count=0;

        while (scanner.hasNext()) {
            String word = scanner.next();
            word = word.toLowerCase().trim();
            if(word.length()>1)  {
                word = word.replaceAll("[,.-]", "");
                word = word.replace("\"", "");
            }

            if(!Arrays.asList(stopWords).contains(word) && !word.isEmpty()) {
                map.computeIfPresent(word, (key, value) -> ++value);
                map.putIfAbsent(word, 1);
                count++;
            }
        }
    }

    public int countTotal() {
        //return map.values().stream().mapToInt(i -> i).sum();
        return count;
    }

    public int countDistinct() {
        return map.size();
    }

    public List<String> mostOften(int k) {
        return map.keySet().stream().sorted(Comparator.comparing(map::get).reversed().thenComparing(Object::toString)).limit(k).collect(Collectors.toList());
    }
}

public class TermFrequencyTest {
    public static void main(String[] args) throws FileNotFoundException {
        String[] stop = new String[] { "во", "и", "се", "за", "ќе", "да", "од",
                "ги", "е", "со", "не", "тоа", "кои", "до", "го", "или", "дека",
                "што", "на", "а", "но", "кој", "ја" };
        TermFrequency tf = new TermFrequency(System.in,
                stop);
        System.out.println(tf.countTotal());
        System.out.println(tf.countDistinct());
        System.out.println(tf.mostOften(10));
    }
}
