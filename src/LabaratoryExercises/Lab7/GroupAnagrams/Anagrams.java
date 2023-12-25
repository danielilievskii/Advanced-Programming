package LabaratoryExercises.Lab7.GroupAnagrams;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class Anagrams {

    public static void main(String[] args) {
        findAll(System.in);
    }
//    public static void findAll(InputStream inputStream) {
//        Map<String, List<String>> anagrams = new TreeMap<>();
//        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
//        br.lines().forEach(line -> {
//            char [] wordChars = line.toCharArray();
//            Arrays.sort(wordChars);
//            String wordString = new String(wordChars);
//            anagrams.putIfAbsent(wordString, new ArrayList<>());
//            anagrams.get(wordString).add(line);
//        });
//
//        anagrams.values().forEach(list -> System.out.println(String.join(" ", list)));
//    }

    public static void findAll(InputStream inputStream) {
        Map<String, List<String>> anagrams = new TreeMap<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        br.lines().forEach(line -> {
            String key = anagrams.keySet().stream().filter(k -> isAnagram(k,line)).findFirst().orElse(null);

            if(key!=null) {
                anagrams.get(key).add(line);
            } else {
                List<String> list = new ArrayList<>();
                list.add(line);
                anagrams.put(line, list);

            }
        });

        anagrams.values().forEach(list -> System.out.println(String.join(" ", list)));
    }

    public static boolean isAnagram(String word1, String word2) {
        if(word1.length() != word2.length()) return false;

        char[] char1 = word1.toCharArray();
        char[] char2 = word2.toCharArray();

        Arrays.sort(char1);
        Arrays.sort(char2);

        return Arrays.equals(char1, char2);
    }
}

