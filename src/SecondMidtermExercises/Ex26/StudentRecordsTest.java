package SecondMidtermExercises.Ex26;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * January 2016 Exam problem 1
 */
class Record {
    String code;
    String major;
    List<Integer> grades;

    public Record(String code, String major, List<Integer> grades) {
        this.code = code;
        this.major = major;
        this.grades = grades;
    }
    public double getAverage() {
        return this.grades.stream().mapToDouble(i -> i).average().orElse(0);
    }

    public String getCode() {
        return code;
    }
    public List<Integer> getGrades() {
        return grades;
    }
    @Override
    public String toString() {
        return String.format("%s %.2f", getCode(), getAverage());
    }
}

class RecordFactory {
    public static Record createRecord(String line) {
        String[] recordParts = line.split("\\s+");
        String code = recordParts[0];
        String major = recordParts[1];
//        List<Integer> grades = Arrays.stream(recordParts).skip(2)
//                .map(Integer::parseInt)
//                .collect(Collectors.toList());

        List<Integer> grades = new ArrayList<>();
        for (int i = 2; i < recordParts.length; i++) {
            grades.add(Integer.parseInt(recordParts[i]));
        }
        return new Record(code, major, grades);
    }
}
class StudentRecords {

    Map<String, List<Record>> studentRecordsByMajor;
    StudentRecords() {
        this.studentRecordsByMajor = new TreeMap<>();
    }
    public int readRecords(InputStream inputStream) {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        br.lines().forEach(line -> {
            String[] recordParts = line.split("\\s+");
            String major = recordParts[1];
            Record newRecord = RecordFactory.createRecord(line);

            studentRecordsByMajor.putIfAbsent(major, new ArrayList<>());
            studentRecordsByMajor.get(major).add(newRecord);
        });

        return (int) studentRecordsByMajor.values().stream().flatMap(Collection::stream).count();
        //return (int) studentRecordsByMajor.values().stream().mapToLong(Collection::size).sum();
    }
    public void writeTable(OutputStream outputStream) {
        PrintWriter pw = new PrintWriter(outputStream);
        this.studentRecordsByMajor.entrySet().forEach(entry -> {
            pw.println(entry.getKey());
            entry.getValue().stream()
                    .sorted(Comparator
                            .comparing(Record::getAverage)
                            .reversed()
                            .thenComparing(Record::getCode))
                    .forEach(pw::println);
        });
        pw.flush();
    }

    public int calculateGrades(List<Record> records, int grade) {
        return (int) records.stream()
                .map(Record::getGrades)
                .flatMap(Collection::stream)
                .filter(i -> i==grade).count();
    }

    public String printSpecialChars(int grades) {
        StringBuilder sb = new StringBuilder();
        double numSpecialChars = Math.ceil(grades/10.0);
        for(int i=0; i<numSpecialChars; i++) {
            sb.append("*");
        }
        return sb.toString();
    }
    public void writeDistribution(OutputStream outputStream) {
        PrintWriter pw = new PrintWriter(outputStream);
        //Comparator<Map.Entry<String, List<Record>>> comparatorByTens = Comparator.comparing(entry -> calculateGrades(entry.getValue(), 10));
        studentRecordsByMajor.entrySet().stream()
                .sorted(Comparator
                        .comparing(entry -> -calculateGrades(entry.getValue(), 10)))
                .forEach(entry -> {
                    System.out.println(entry.getKey());
                    for(int i=6; i<=10; i++) {
                        int wantedGrades = calculateGrades(entry.getValue(), i);
                        System.out.println(String.format("%2d | %s(%d)", i, printSpecialChars(wantedGrades), wantedGrades));
                    }
                });
        pw.flush();

    }
}
public class StudentRecordsTest {
    public static void main(String[] args) {
        System.out.println("=== READING RECORDS ===");
        StudentRecords studentRecords = new StudentRecords();
        int total = studentRecords.readRecords(System.in);
        System.out.printf("Total records: %d\n", total);
        System.out.println("=== WRITING TABLE ===");
        studentRecords.writeTable(System.out);
        System.out.println("=== WRITING DISTRIBUTION ===");
        studentRecords.writeDistribution(System.out);
    }
}
