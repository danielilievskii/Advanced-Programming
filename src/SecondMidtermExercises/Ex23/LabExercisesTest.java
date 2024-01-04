package SecondMidtermExercises.Ex23;

import java.util.*;
import java.util.stream.Collectors;

class Student {
    String index;
    List<Integer> points;

    public Student(String index, List<Integer> points) {
        this.index = index;
        this.points = points;
    }
    public double getTotalPoints() {
        return points.stream().mapToInt(i -> i).sum();
    }
    public double getAveragePoints() {
        return getTotalPoints()/10.0;
    }
    public String getIndex() {
        return index;
    }

    public int getNotAttendances() {
        return 10 - points.size();
    }

    public boolean hasSignature() {
        return getNotAttendances() <= 2;
    }

    public int getYearOfStudies() {
        return 20 - Integer.parseInt(index.substring(0, 2));
    }

    @Override
    public String toString() {
        return String.format("%s %s %.2f", index, hasSignature() ? "YES" : "NO", getAveragePoints());
    }
}

class LabExercises {

    List<Student> students;

    public LabExercises() {
        this.students = new ArrayList<>();
    }
    public void addStudent (Student student) {
        students.add(student);
    }

    public void printByAveragePoints(boolean ascending, int n) {
        Comparator<Student> comparator = Comparator.comparing(Student::getAveragePoints).thenComparing(Student::getIndex);
        if(!ascending) {
            comparator=comparator.reversed();
        }
        students.stream().sorted(comparator).limit(n).forEach(System.out::println);
    }

    public List<Student> failedStudents () {
        return students.stream()
                .filter(student -> !student.hasSignature())
                .sorted(Comparator.comparing(Student::getIndex).thenComparing(Student::getAveragePoints))
                .collect(Collectors.toList());
    }

    public Map<Integer,Double> getStatisticsByYear() {
       return students.stream()
               .filter(Student::hasSignature)
               .collect(Collectors.groupingBy(Student::getYearOfStudies, Collectors.averagingDouble(Student::getAveragePoints)));
    }
}

public class LabExercisesTest {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LabExercises labExercises = new LabExercises();
        while (sc.hasNextLine()) {
            String input = sc.nextLine();
            String[] parts = input.split("\\s+");
            String index = parts[0];
            List<Integer> points = Arrays.stream(parts).skip(1)
                    .mapToInt(Integer::parseInt)
                    .boxed()
                    .collect(Collectors.toList());

            labExercises.addStudent(new Student(index, points));
        }

        System.out.println("===printByAveragePoints (ascending)===");
        labExercises.printByAveragePoints(true, 100);
        System.out.println("===printByAveragePoints (descending)===");
        labExercises.printByAveragePoints(false, 100);
        System.out.println("===failed students===");
        labExercises.failedStudents().forEach(System.out::println);
        System.out.println("===statistics by year");
        labExercises.getStatisticsByYear().entrySet().stream()
                .map(entry -> String.format("%d : %.2f", entry.getKey(), entry.getValue()))
                .forEach(System.out::println);

    }
}