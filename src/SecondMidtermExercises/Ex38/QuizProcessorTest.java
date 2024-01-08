package SecondMidtermExercises.Ex38;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

class QuizProcessor {

    public static Map<String, Double> processAnswers(InputStream is) {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        Map<String, Double> studentPoints = new TreeMap<>();
        br.lines().forEach(line -> {
            String[] infoParts = line.split(";");
            String index = infoParts[0];
            List<String> correctAnswers = new ArrayList<>(List.of(infoParts[1].split(", ")));
            List<String> studentAnswers = new ArrayList<>(List.of(infoParts[2].split(", ")));

            if(correctAnswers.size() != studentAnswers.size()) {
                System.out.println("A quiz must have same number of correct and selected answers");
                return;
            }
            double points = 0;

            for(int i=0; i<correctAnswers.size(); i++) {
                if (correctAnswers.get(i).equals(studentAnswers.get(i))) {
                    points+=1;
                } else points-=0.25;
            }
            studentPoints.put(index, points);

        });
        return studentPoints;
    }
}

public class QuizProcessorTest {
    public static void main(String[] args) {
        QuizProcessor.processAnswers(System.in).forEach((k, v) -> System.out.printf("%s -> %.2f%n", k, v));
    }
}
