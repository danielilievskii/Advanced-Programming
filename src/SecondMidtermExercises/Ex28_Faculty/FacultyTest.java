package SecondMidtermExercises.Ex28_Faculty;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class OperationNotAllowedException extends Exception {
    public OperationNotAllowedException(String message) {
        super(message);
    }
}

class Course implements Comparable<Course> {
    String name;
    List<Integer> grades;
    public Course(String name) {
        this.name = name;
        this.grades = new ArrayList<>();
    }
    public void addGrade(int grade) {
        grades.add(grade);
    }
    public int getStudentsCount() {
        return grades.size();
    }
    public double getCourseAverage() {
        return grades.stream().mapToInt(i -> i).average().orElse(5.0);
    }
    public String getName() {
        return name;
    }

    @Override
    public int compareTo(Course o) {
        return Comparator.comparing(Course::getStudentsCount)
                .thenComparing(Course::getCourseAverage)
                .thenComparing(Course::getName)
                .compare(o, this);
    }
    @Override
    public String toString() {
        return String.format("%s %d %.2f", getName(), getStudentsCount(), getCourseAverage());
    }
}

abstract class Student {
    String ID;
    Map<Integer, Map<String, Integer>> courseGradesByTerm;
    public Student(String ID) {
        this.ID = ID;
        courseGradesByTerm = new HashMap<>();
    }
    public void addGrade(Integer term, String courseName, Integer grade) throws OperationNotAllowedException {
        if(!courseGradesByTerm.containsKey(term)) {
            throw new OperationNotAllowedException(String.format("Term %d is not possible for student with ID %s", term, ID));
        }
        if(courseGradesByTerm.get(term).size() == 3) {
            throw new OperationNotAllowedException(String.format("Student %s already has 3 grades in term %d", ID, term));
        }
        courseGradesByTerm.putIfAbsent(term, new HashMap<>());
        courseGradesByTerm.get(term).putIfAbsent(courseName, grade);
    }
    public abstract boolean isGraduated();

    public double averageGrade() {
        return courseGradesByTerm.values()
                .stream()
                .flatMap(innerMap -> innerMap.values().stream())
                .mapToInt(i -> i)
                .average()
                .orElse(5.0);
    }

    public int passedCourses() {
        return (int) courseGradesByTerm.values()
                .stream()
                .flatMap(innerMap -> innerMap
                        .values().stream())
                .mapToInt(i -> i)
                .filter(grade -> grade>6.0).count();
    }

    public String getDetailedReport() {
        StringBuilder sb = new StringBuilder();
        sb.append(ID).append("\n");
        courseGradesByTerm.entrySet().forEach(entry -> {
            sb.append(entry.getKey()).append("\n");

            Map<String, Integer> coursesForCurrTerm = entry.getValue();
            sb.append("Courses for term: ").append(coursesForCurrTerm.size()).append("\n");

            double averageGradeForCurrTerm =  coursesForCurrTerm.values().stream().mapToInt(i -> i).average().orElse(5.0);
            sb.append("Average grade for term: ").append(String.format("%.2f", averageGradeForCurrTerm)).append("\n");
        });
        return sb.toString();
    }
    String getGraduationLog() {
        return String.format("Student with ID %s graduated with average grade %.2f", ID, averageGrade());
    }

    @Override
    public String toString() {
        return String.format("Student: %s Courses passed: %s Average grade: %.2f", ID, passedCourses(), averageGrade());
    }
}

class ThreeYearStudiesStudent extends Student {

    public ThreeYearStudiesStudent(String ID) {
        super(ID);
    }
    @Override
    public boolean isGraduated() {
//        return courseGradesByTerm.values()
//                .stream()
//                .flatMap(innerMap -> innerMap.values().stream())
//                .mapToInt(i -> i).filter(grade -> grade>=6).count() > 18;
        return passedCourses() >= 18;
    }
    @Override
    public String toString() {
        return super.toString();
    }
    String getGraduationLog() {
        return String.format("Student with ID %s graduated with average grade %.2f in 3 years.", ID, averageGrade());
    }
}

class FourYearStudiesStudent extends Student {

    public FourYearStudiesStudent(String ID) {
        super(ID);
    }
    @Override
    public boolean isGraduated() {
        return courseGradesByTerm.values()
                .stream()
                .flatMap(innerMap -> innerMap.values().stream())
                .mapToInt(i -> i).filter(grade -> grade>=6).count() >= 24;
    }

    @Override
    public String toString() {
        return super.toString();
    }
    String getGraduationLog() {
        return String.format("Student with ID %s graduated with average grade %.2f in 4 years.", ID, averageGrade());
    }
}

class Faculty {

    Map<String, Course> coursesByName;
    Map<String, Student> studentsByID;
    List<String> logs = new ArrayList<>();
    public Faculty() {
        coursesByName = new TreeMap<>();
        studentsByID = new HashMap<>();

    }
    void addStudent(String id, int yearsOfStudies) {
        if(yearsOfStudies == 3) {
            studentsByID.put(id, new ThreeYearStudiesStudent(id));
        } else {
            studentsByID.put(id, new FourYearStudiesStudent(id));
        }
    }
    void addGradeToStudent(String studentId, int term, String courseName, int grade) throws OperationNotAllowedException {
        Student student = studentsByID.get(studentId);
        student.addGrade(term, courseName, grade);

        coursesByName.putIfAbsent(courseName, new Course(courseName));
        coursesByName.get(courseName).addGrade(grade);

        if(student.isGraduated()) {
            logs.add(student.getGraduationLog());
            studentsByID.remove(studentId);
        }
    }
    String getFacultyLogs() {
        return String.join("\n", logs);
    }
    String getDetailedReportForStudent(String id) {
        return studentsByID.get(id).getDetailedReport();
    }
    void printFirstNStudents(int n) {
        Comparator<Student> comparator = Comparator.comparing(Student::passedCourses)
                .thenComparing(Student::averageGrade)
                .reversed();
        Set<Student> wantedStudents = new TreeSet<>(comparator);
        wantedStudents.addAll(studentsByID.values());
        wantedStudents.stream().limit(n).forEach(System.out::println);
    }
    void printCourses() {
        TreeSet<Course> coursesSet = new TreeSet<>();
        coursesSet.addAll(coursesByName.values());
        coursesSet.forEach(System.out::println);
    }
}

public class FacultyTest {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int testCase = sc.nextInt();

        if (testCase == 1) {
            System.out.println("TESTING addStudent AND printFirstNStudents");
            Faculty faculty = new Faculty();
            for (int i = 0; i < 10; i++) {
                faculty.addStudent("student" + i, (i % 2 == 0) ? 3 : 4);
            }
            faculty.printFirstNStudents(10);

        } else if (testCase == 2) {
            System.out.println("TESTING addGrade and exception");
            Faculty faculty = new Faculty();
            faculty.addStudent("123", 3);
            faculty.addStudent("1234", 4);
            try {
                faculty.addGradeToStudent("123", 7, "NP", 10);
            } catch (OperationNotAllowedException e) {
                System.out.println(e.getMessage());
            }
            try {
                faculty.addGradeToStudent("1234", 9, "NP", 8);
            } catch (OperationNotAllowedException e) {
                System.out.println(e.getMessage());
            }
        } else if (testCase == 3) {
            System.out.println("TESTING addGrade and exception");
            Faculty faculty = new Faculty();
            faculty.addStudent("123", 3);
            faculty.addStudent("1234", 4);
            for (int i = 0; i < 4; i++) {
                try {
                    faculty.addGradeToStudent("123", 1, "course" + i, 10);
                } catch (OperationNotAllowedException e) {
                    System.out.println(e.getMessage());
                }
            }
            for (int i = 0; i < 4; i++) {
                try {
                    faculty.addGradeToStudent("1234", 1, "course" + i, 10);
                } catch (OperationNotAllowedException e) {
                    System.out.println(e.getMessage());
                }
            }
        } else if (testCase == 4) {
            System.out.println("Testing addGrade for graduation");
            Faculty faculty = new Faculty();
            faculty.addStudent("123", 3);
            faculty.addStudent("1234", 4);
            int counter = 1;
            for (int i = 1; i <= 6; i++) {
                for (int j = 1; j <= 3; j++) {
                    try {
                        faculty.addGradeToStudent("123", i, "course" + counter, (i % 2 == 0) ? 7 : 8);
                    } catch (OperationNotAllowedException e) {
                        System.out.println(e.getMessage());
                    }
                    ++counter;
                }
            }
            counter = 1;
            for (int i = 1; i <= 8; i++) {
                for (int j = 1; j <= 3; j++) {
                    try {
                        faculty.addGradeToStudent("1234", i, "course" + counter, (j % 2 == 0) ? 7 : 10);
                    } catch (OperationNotAllowedException e) {
                        System.out.println(e.getMessage());
                    }
                    ++counter;
                }
            }
            System.out.println("LOGS");
            System.out.println(faculty.getFacultyLogs());
            System.out.println("PRINT STUDENTS (there shouldn't be anything after this line!");
            faculty.printFirstNStudents(2);
        } else if (testCase == 5 || testCase == 6 || testCase == 7) {
            System.out.println("Testing addGrade and printFirstNStudents (not graduated student)");
            Faculty faculty = new Faculty();
            for (int i = 1; i <= 10; i++) {
                faculty.addStudent("student" + i, ((i % 2) == 1 ? 3 : 4));
                int courseCounter = 1;
                for (int j = 1; j < ((i % 2 == 1) ? 6 : 8); j++) {
                    for (int k = 1; k <= ((j % 2 == 1) ? 3 : 2); k++) {
                        try {
                            faculty.addGradeToStudent("student" + i, j, ("course" + courseCounter), i % 5 + 6);
                        } catch (OperationNotAllowedException e) {
                            System.out.println(e.getMessage());
                        }
                        ++courseCounter;
                    }
                }
            }
            if (testCase == 5)
                faculty.printFirstNStudents(10);
            else if (testCase == 6)
                faculty.printFirstNStudents(3);
            else
                faculty.printFirstNStudents(20);
        } else if (testCase == 8 || testCase == 9) {
            System.out.println("TESTING DETAILED REPORT");
            Faculty faculty = new Faculty();
            faculty.addStudent("student1", ((testCase == 8) ? 3 : 4));
            int grade = 6;
            int counterCounter = 1;
            for (int i = 1; i < ((testCase == 8) ? 6 : 8); i++) {
                for (int j = 1; j < 3; j++) {
                    try {
                        faculty.addGradeToStudent("student1", i, "course" + counterCounter, grade);
                    } catch (OperationNotAllowedException e) {
                        e.printStackTrace();
                    }
                    grade++;
                    if (grade == 10)
                        grade = 5;
                    ++counterCounter;
                }
            }
            System.out.println(faculty.getDetailedReportForStudent("student1"));
        } else if (testCase==10) {
            System.out.println("TESTING PRINT COURSES");
            Faculty faculty = new Faculty();
            for (int i = 1; i <= 10; i++) {
                faculty.addStudent("student" + i, ((i % 2) == 1 ? 3 : 4));
                int courseCounter = 1;
                for (int j = 1; j < ((i % 2 == 1) ? 6 : 8); j++) {
                    for (int k = 1; k <= ((j % 2 == 1) ? 3 : 2); k++) {
                        int grade = sc.nextInt();
                        try {
                            faculty.addGradeToStudent("student" + i, j, ("course" + courseCounter), grade);
                        } catch (OperationNotAllowedException e) {
                            System.out.println(e.getMessage());
                        }
                        ++courseCounter;
                    }
                }
            }
            faculty.printCourses();
        } else if (testCase==11) {
            System.out.println("INTEGRATION TEST");
            Faculty faculty = new Faculty();
            for (int i = 1; i <= 10; i++) {
                faculty.addStudent("student" + i, ((i % 2) == 1 ? 3 : 4));
                int courseCounter = 1;
                for (int j = 1; j <= ((i % 2 == 1) ? 6 : 8); j++) {
                    for (int k = 1; k <= ((j % 2 == 1) ? 2 : 3); k++) {
                        int grade = sc.nextInt();
                        try {
                            faculty.addGradeToStudent("student" + i, j, ("course" + courseCounter), grade);
                        } catch (OperationNotAllowedException e) {
                            System.out.println(e.getMessage());
                        }
                        ++courseCounter;
                    }
                }

            }

            for (int i=11;i<15;i++) {
                faculty.addStudent("student" + i, ((i % 2) == 1 ? 3 : 4));
                int courseCounter = 1;
                for (int j = 1; j <= ((i % 2 == 1) ? 6 : 8); j++) {
                    for (int k = 1; k <= 3; k++) {
                        int grade = sc.nextInt();
                        try {
                            faculty.addGradeToStudent("student" + i, j, ("course" + courseCounter), grade);
                        } catch (OperationNotAllowedException e) {
                            System.out.println(e.getMessage());
                        }
                        ++courseCounter;
                    }
                }
            }
            System.out.println("LOGS");
            System.out.println(faculty.getFacultyLogs());
            System.out.println("DETAILED REPORT FOR STUDENT");
            System.out.println(faculty.getDetailedReportForStudent("student2"));
            try {
                System.out.println(faculty.getDetailedReportForStudent("student11"));
                System.out.println("The graduated students should be deleted!!!");
            } catch (NullPointerException e) {
                System.out.println("The graduated students are really deleted");
            }
            System.out.println("FIRST N STUDENTS");
            faculty.printFirstNStudents(10);
            System.out.println("COURSES");
            faculty.printCourses();
        }
    }
}
