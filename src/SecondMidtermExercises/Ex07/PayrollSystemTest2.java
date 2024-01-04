package SecondMidtermExercises.Ex07;

import java.util.*;
import java.util.stream.Collectors;

import java.io.*;

enum EmployeeType {
    HOURLY, FREELANCE
}

class BonusNotAllowedException extends Exception {
    public BonusNotAllowedException(String message) {
        super(message);
    }
}

abstract class Employee implements Comparable<Employee> {
    String ID;
    String level;

    public Employee(String ID, String level) {
        this.ID = ID;
        this.level = level;
    }

    public abstract double getSalary();
    public abstract double getSalaryWithBonus();
    public abstract double getOvertimeSalary();
    public abstract double getBonus();

    public abstract int getTicketsCount();

    public String getLevel() {
        return level;
    }

    public int compareTo(Employee other) {
        return Comparator.comparing(Employee::getSalary).thenComparing(Employee::getLevel).reversed().compare(this, other);
    }

    public abstract EmployeeType getEmployeeType();
}

class HourlyEmployee extends Employee {
    double hours;
    double overtimeHours;
    double regularHours;
    double hourlyRate;
    double percentageBonus;

    public HourlyEmployee(String ID, String level, double hours, double hourlyRate, double percentageBonus) {
        super(ID, level);
        this.hours=hours;
        this.hourlyRate = hourlyRate;
        this.percentageBonus = percentageBonus;
        this.overtimeHours = Math.max(0, hours-40);
        this.regularHours = hours-overtimeHours;
    }
    public double getSalary() {
        return (regularHours * hourlyRate + overtimeHours * hourlyRate * 1.5);
    }
    public double getSalaryWithBonus() {
        if(percentageBonus>0) return getSalary() * (1 + percentageBonus/100);
        else return getSalary();
    }
    public double getOvertimeSalary() {
        return overtimeHours * hourlyRate * 1.5;
    }

    @Override
    public double getBonus() {
        return getSalaryWithBonus()-getSalary();
    }

    @Override
    public EmployeeType getEmployeeType() {
        return EmployeeType.HOURLY;
    }

    public int getTicketsCount() {
        return 0;
    }

    @Override
    public String toString() {
        //Employee ID: 157f3d Level: level10 Salary: 2390.72 Regular hours: 40.00 Overtime hours: 23.14
        return String.format("Employee ID: %s Level: %s Salary: %.2f Regular hours: %.2f Overtime hours: %.2f Bonus: %.2f", ID, level, getSalary(), regularHours, overtimeHours, getBonus());
    }
}

class FreelanceEmployee extends Employee {
    private List<Double> ticketPoints;
    double ticketRate;
    double fixedBonus;

    public FreelanceEmployee(String ID, String level, List<Double> ticketPoints, double ticketRate, double fixedBonus) {
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
    public double getSalaryWithBonus() {
        return getSalary() + fixedBonus;
    }

    public int getTicketsCount() {
        return ticketPoints.size();
    }
    public double getOvertimeSalary() {
        return 0;
    }

    @Override
    public double getBonus() {
        return getSalaryWithBonus()-getSalary();
    }

    @Override
    public EmployeeType getEmployeeType() {
        return EmployeeType.FREELANCE;
    }
    @Override
    public String toString() {
        //Employee ID: 596ed2 Level: level10 Salary: 1290.00 Tickets count: 9 Tickets points: 43
        return String.format("Employee ID: %s Level: %s Salary: %.2f Tickets count: %d Tickets points: %.0f Bonus: %.2f", ID, level, getSalary(), ticketPoints.size(), getTicketPointsSum(), getBonus());
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

    public Employee createEmployee(String line) throws BonusNotAllowedException {

        String[] parts = line.split("\\s+");

        String bonus=null;
        if(parts.length>1) {
            bonus=parts[1];
        }
        String[] employeeParts = parts[0].split(";");
        String type = employeeParts[0];
        String ID = employeeParts[1];
        String level = employeeParts[2];
        Employee employee;
            if(type.equals("H")) {
                double hourlyRate = hourlyRateByLevel.get(level);
                double hours = Double.parseDouble(employeeParts[3]);
                double percentageBonus=0;
                if(bonus!=null) {
                    percentageBonus = Double.parseDouble(bonus.substring(0, bonus.length()-1));
                    if(percentageBonus > 20) {
                        throw new BonusNotAllowedException("");
                    }
                }
                employee = new HourlyEmployee(ID, level, hours, hourlyRate, percentageBonus);
                employees.add(employee);
            } else {
                double ticketRate = ticketRateByLevel.get(level);
                List<Double> ticketPoints = Arrays.stream(employeeParts).skip(3).map(Double::parseDouble).collect(Collectors.toList());
                double fixedBonus = 0;
                if(bonus!=null) {
                    fixedBonus = Double.parseDouble(bonus);
                    if(fixedBonus>1000) {
                        throw new BonusNotAllowedException("");
                    }
                }
                employee = new FreelanceEmployee(ID, level, ticketPoints, ticketRate, fixedBonus);
                employees.add(employee);
            }
            return employee;

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

    public Map<String, Double> getOvertimeSalaryForLevels() {
        return employees.stream()
                .filter(employee ->  employee.getEmployeeType().equals(EmployeeType.HOURLY))
                .collect(Collectors.groupingBy(
                        Employee::getLevel,
                        Collectors.summingDouble(Employee::getOvertimeSalary)
                ));
    }

    public void printStatisticsForOvertimeSalary() {
        return;
    }

    public Map<String, Integer> ticketsDoneByLevel() {
        return employees.stream()
                .filter(employee ->  employee.getEmployeeType().equals(EmployeeType.HOURLY))
                .collect(Collectors.groupingBy(
                        Employee::getLevel,
                        Collectors.summingInt(Employee::getTicketsCount)
                ));
    }

    Collection<Employee> getFirstNEmployeesByBonus (int n) {
        return employees.stream()
                .sorted(Comparator.comparing(Employee::getBonus).reversed()).limit(n).collect(Collectors.toList());
    }
}

public class PayrollSystemTest2 {

    public static void main(String[] args) {

        Map<String, Double> hourlyRateByLevel = new LinkedHashMap<>();
        Map<String, Double> ticketRateByLevel = new LinkedHashMap<>();
        for (int i = 1; i <= 10; i++) {
            hourlyRateByLevel.put("level" + i, 11 + i * 2.2);
            ticketRateByLevel.put("level" + i, 5.5 + i * 2.5);
        }

        Scanner sc = new Scanner(System.in);

        int employeesCount = Integer.parseInt(sc.nextLine());

        PayrollSystem ps = new PayrollSystem(hourlyRateByLevel, ticketRateByLevel);
        Employee emp = null;
        for (int i = 0; i < employeesCount; i++) {
            try {
                emp = ps.createEmployee(sc.nextLine());
            } catch (BonusNotAllowedException e) {
                System.out.println(e.getMessage());
            }
        }

        int testCase = Integer.parseInt(sc.nextLine());

        switch (testCase) {
            case 1: //Testing createEmployee
                if (emp != null)
                    System.out.println(emp);
                break;
            case 2: //Testing getOvertimeSalaryForLevels()
                ps.getOvertimeSalaryForLevels().forEach((level, overtimeSalary) -> {
                    System.out.printf("Level: %s Overtime salary: %.2f\n", level, overtimeSalary);
                });
                break;
            case 3: //Testing printStatisticsForOvertimeSalary()
                ps.printStatisticsForOvertimeSalary();
                break;
            case 4: //Testing ticketsDoneByLevel
                ps.ticketsDoneByLevel().forEach((level, overtimeSalary) -> {
                    System.out.printf("Level: %s Tickets by level: %d\n", level, overtimeSalary);
                });
                break;
            case 5: //Testing getFirstNEmployeesByBonus (int n)
                ps.getFirstNEmployeesByBonus(Integer.parseInt(sc.nextLine())).forEach(System.out::println);
                break;
        }

    }
}
