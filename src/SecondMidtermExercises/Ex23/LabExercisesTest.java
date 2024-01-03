//package SecondMidtermExercises.Ex23;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Map;
//import java.util.Scanner;
//import java.util.stream.Collectors;
//
//class Student {
//    String index;
//    List<Integer> points;
//
//    public Student(String index, List<Integer> points) {
//        this.index = index;
//        this.points = points;
//    }
//    public int getTotalPoints() {
//        return points.stream().mapToInt(i -> i).sum();
//    }
//    public double getAveragePoints() {
//        return getTotalPoints()/10.0;
//    }
//    public String getIndex() {
//        return index;
//    }
//
//    public int getNotAttendances() {
//        return (int) points.stream().filter(i -> i==0).count();
//    }
//}
//
//class LabExercises {
//    public void addStudent (Student student) {
//
//    }
//
//    public void printByAveragePoints(boolean ascending, int n) {
//
//    }
//
//    public List<Student> failedStudents () {
//        return null;
//    }
//
//    public Map<Integer,Double> getStatisticsByYear() {
//        return null;
//    }
//}
//
//public class LabExercisesTest {
//
//    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in);
//        LabExercises labExercises = new LabExercises();
//        while (sc.hasNextLine()) {
//            String input = sc.nextLine();
//            String[] parts = input.split("\\s+");
//            String index = parts[0];
//            List<Integer> points = Arrays.stream(parts).skip(1)
//                    .mapToInt(Integer::parseInt)
//                    .boxed()
//                    .collect(Collectors.toList());
//
//            labExercises.addStudent(new Student(index, points));
//        }
//
//        System.out.println("===printByAveragePoints (ascending)===");
//        labExercises.printByAveragePoints(true, 100);
//        System.out.println("===printByAveragePoints (descending)===");
//        labExercises.printByAveragePoints(false, 100);
//        System.out.println("===failed students===");
//        labExercises.failedStudents().forEach(System.out::println);
//        System.out.println("===statistics by year");
//        labExercises.getStatisticsByYear().entrySet().stream()
//                .map(entry -> String.format("%d : %.2f", entry.getKey(), entry.getValue()))
//                .forEach(System.out::println);
//
//    }
//}