package SecondMidtermExercises.Ex06_PayrollSystem;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

abstract class Employee implements Comparable<Employee> {
    String ID;
    String level;

    public Employee(String ID, String level) {
        this.ID = ID;
        this.level = level;
    }

    public abstract double getSalary();

    public String getLevel() {
        return level;
    }

    public int compareTo(Employee other) {
        return Comparator.comparing(Employee::getSalary).thenComparing(Employee::getLevel).reversed().compare(this, other);
    }
}

class HourlyEmployee extends Employee {
    double hours;
    double hourlyRate;

    public HourlyEmployee(String ID, String level, double hours, double hourlyRate) {
        super(ID, level);
        this.hours=hours;
        this.hourlyRate = hourlyRate;
    }

    public double getSalary() {
        if(hours<=40) {
            return hourlyRate * hours;
        } else {
            return 40 * hourlyRate + (hours-40) *hourlyRate * 1.5;
        }
    }

    @Override
    public String toString() {
        //Employee ID: 157f3d Level: level10 Salary: 2390.72 Regular hours: 40.00 Overtime hours: 23.14
        return String.format("Employee ID: %s Level: %s Salary: %.2f Regular hours: %.2f Overtime hours: %.2f", ID, level, getSalary(), hours > 40 ? 40 : hours, hours > 40 ? hours-40 : 0);
    }
}

class FreelanceEmployee extends Employee {
    private List<Double> ticketPoints;
    double ticketRate;

    public FreelanceEmployee(String ID, String level, List<Double> ticketPoints, double ticketRate) {
        super(ID, level);
        this.ticketPoints = ticketPoints;
        this.ticketRate = ticketRate;
    }
    public double getTicketPointsSum() {
        return ticketPoints.stream().mapToDouble(i -> i).sum();
    }
    public double getSalary() {
        return getTicketPointsSum() * ticketRate;
    }

    @Override
    public String toString() {
        //Employee ID: 596ed2 Level: level10 Salary: 1290.00 Tickets count: 9 Tickets points: 43
        return String.format("Employee ID: %s Level: %s Salary: %.2f Tickets count: %d Tickets points: %.0f", ID, level, getSalary(), ticketPoints.size(), getTicketPointsSum());
    }
}

class PayrollSystem {
    Map<String, Double> hourlyRateByLevel;
    Map<String,Double> ticketRateByLevel;

    List<Employee> employees;

    public PayrollSystem(Map<String,Double> hourlyRateByLevel, Map<String,Double> ticketRateByLevel) {
        this.hourlyRateByLevel = hourlyRateByLevel;
        this.ticketRateByLevel = ticketRateByLevel;
        this.employees = new ArrayList<>();
    }

    public void readEmployees(InputStream in) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        bufferedReader.lines().forEach(line -> {
            String[] employeeParts = line.split(";");
            String type = employeeParts[0];
            String ID = employeeParts[1];
            String level = employeeParts[2];
            if(type.equals("H")) {
                double hourlyRate = hourlyRateByLevel.get(level);
                double hours = Double.parseDouble(employeeParts[3]);
                employees.add(new HourlyEmployee(ID, level, hours, hourlyRate));
            } else {
                double ticketRate = ticketRateByLevel.get(level);
                List<Double> ticketPoints = Arrays.stream(employeeParts).skip(3).map(Double::parseDouble).collect(Collectors.toList());
                employees.add(new FreelanceEmployee(ID, level, ticketPoints, ticketRate));
            }
        });
    }

    public Map<String, Set<Employee>> printEmployeesByLevels(PrintStream out, Set<String> levels) {
        Map<String, Set<Employee>> map = employees.stream().collect(Collectors.groupingBy(
                Employee::getLevel,
                TreeMap::new,
                Collectors.toCollection(TreeSet::new)
        ));

        Set<String> allLevels = new HashSet<>(map.keySet());

        allLevels.stream()
                .filter(l -> !levels.contains(l))
                .forEach(map::remove);

        return map;
    }
}

public class PayrollSystemTest {

    public static void main(String[] args) {

        Map<String, Double> hourlyRateByLevel = new LinkedHashMap<>();
        Map<String, Double> ticketRateByLevel = new LinkedHashMap<>();
        for (int i = 1; i <= 10; i++) {
            hourlyRateByLevel.put("level" + i, 10 + i * 2.2);
            ticketRateByLevel.put("level" + i, 5 + i * 2.5);
        }

        PayrollSystem payrollSystem = new PayrollSystem(hourlyRateByLevel, ticketRateByLevel);

        System.out.println("READING OF THE EMPLOYEES DATA");
        payrollSystem.readEmployees(System.in);

        System.out.println("PRINTING EMPLOYEES BY LEVEL");
        Set<String> levels = new LinkedHashSet<>();
        for (int i=5;i<=10;i++) {
            levels.add("level"+i);
        }
        Map<String, Set<Employee>> result = payrollSystem.printEmployeesByLevels(System.out, levels);
        result.forEach((level, employees) -> {
            System.out.println("LEVEL: "+ level);
            System.out.println("Employees: ");
            employees.forEach(System.out::println);
            System.out.println("------------");
        });


    }
}