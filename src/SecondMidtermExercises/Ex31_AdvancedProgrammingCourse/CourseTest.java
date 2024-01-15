package SecondMidtermExercises.Ex31_AdvancedProgrammingCourse;

//package mk.ukim.finki.midterm;

import java.util.*;
import java.util.stream.Collectors;

class InvalidPointsException extends Exception {
    public InvalidPointsException() {
    }
}


class Student {
    String index;
    String name;

    int pointsFirstMidterm;
    int pointsSecondMidterm;
    int pointsLab;

    public Student(String index, String name) {
        this.index = index;
        this.name = name;
    }

    public double getTotalPoints() {
        return pointsFirstMidterm * 0.45 + pointsSecondMidterm * 0.45 + pointsLab;
    }
    public int getGrade() {
        if(getTotalPoints()>=51 && getTotalPoints()<=59.99) {
            return 6;
        }
        if(getTotalPoints()>=60.01 && getTotalPoints()<=69.99) {
            return 7;
        }
        if(getTotalPoints()>=70.00 && getTotalPoints()<=79.99) {
            return 8;
        }
        if(getTotalPoints()>=80.00 && getTotalPoints()<=89.99) {
            return 9;
        }
        if(getTotalPoints()>=90.00 && getTotalPoints()<=100) {
            return 10;
        } else {
            return 5;
        }
    }
    public void setPointsFirstMidterm(int pointsFirstMidterm) {
        this.pointsFirstMidterm = pointsFirstMidterm;
    }
    public void setPointsSecondMidterm(int pointsSecondMidterm) {
        this.pointsSecondMidterm = pointsSecondMidterm;
    }
    public void setPointsLab(int pointsLab) {
        this.pointsLab = pointsLab;
    }

    public String getIndex() {
        return index;
    }

    @Override
    public String toString() {
        return String.format("ID: %s Name: %s First midterm: %d Second midterm %d Labs: %d Summary points: %.2f Grade: %d",
                index, name, pointsFirstMidterm, pointsSecondMidterm, pointsLab, getTotalPoints(), getGrade());
    }
}

class AdvancedProgrammingCourse {
    Map<String, Student> studentMap;

    public AdvancedProgrammingCourse() {
        this.studentMap = new HashMap<>();
    }
    public void addStudent (Student s) {
        studentMap.put(s.getIndex(), s);
    }
    public void updateStudent (String idNumber, String activity, int points) throws InvalidPointsException {
        if(activity.equals("labs") && points > 10) {
            throw new InvalidPointsException();
        }
        if(points > 100) {
            throw new InvalidPointsException();
        }

        Student neededStudent = studentMap.get(idNumber);
        switch (activity) {
            case "midterm1":
                neededStudent.setPointsFirstMidterm(points);
                break;
            case "midterm2":
                neededStudent.setPointsSecondMidterm(points);
                break;
            case "labs":
                neededStudent.setPointsLab(points);
                break;
        }
    }
    public List<Student> getFirstNStudents (int n) {
        return studentMap.values().stream()
                .sorted(Comparator.comparing(Student::getTotalPoints).reversed())
                .limit(n)
                .collect(Collectors.toList());
    }
    public void printStatistics() {
        List<Student> studentsToAnalyse = studentMap.values()
                .stream()
                .filter(student -> student.getGrade()>5)
                .collect(Collectors.toList());

        DoubleSummaryStatistics dss = studentsToAnalyse.stream()
                .mapToDouble(Student::getTotalPoints).summaryStatistics();

        System.out.println(String.format("Count: %d Min: %.2f Average: %.2f Max: %.2f",
                dss.getCount(), dss.getMin(), dss.getAverage(), dss.getMax()));

    }
    public Map<Integer, Integer> getGradeDistribution() {
        Map<Integer, Integer> resultMap = new HashMap<>();
        for(int i=5; i<=10; i++) {
            resultMap.putIfAbsent(i, 0);
        }

        studentMap.values().forEach(student -> {
            int grade = student.getGrade();
            resultMap.computeIfPresent(grade, (k, v) -> ++v);
        });

        return resultMap;
    }
}


public class CourseTest {

    public static void printStudents(List<Student> students) {
        students.forEach(System.out::println);
    }

    public static void printMap(Map<Integer, Integer> map) {
        map.forEach((k, v) -> System.out.printf("%d -> %d%n", k, v));
    }

    public static void main(String[] args)  {
        AdvancedProgrammingCourse advancedProgrammingCourse = new AdvancedProgrammingCourse();

        Scanner sc = new Scanner(System.in);

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] parts = line.split("\\s+");

            String command = parts[0];

            if (command.equals("addStudent")) {
                String id = parts[1];
                String name = parts[2];
                advancedProgrammingCourse.addStudent(new Student(id, name));
            } else if (command.equals("updateStudent")) {
                String idNumber = parts[1];
                String activity = parts[2];
                int points = Integer.parseInt(parts[3]);
                try {
                    advancedProgrammingCourse.updateStudent(idNumber, activity, points);
                } catch (InvalidPointsException e) {
                    throw new RuntimeException(e);
                }
            } else if (command.equals("getFirstNStudents")) {
                int n = Integer.parseInt(parts[1]);
                printStudents(advancedProgrammingCourse.getFirstNStudents(n));
            } else if (command.equals("getGradeDistribution")) {
                printMap(advancedProgrammingCourse.getGradeDistribution());
            } else {
                advancedProgrammingCourse.printStatistics();
            }
        }
    }
}

