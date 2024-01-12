package SecondMidtermExercises.Ex07;

import java.util.*;
import java.util.stream.Collectors;

import java.io.*;

class BonusNotAllowedException extends Exception {
    public BonusNotAllowedException(String message) {
        super(message);
    }
}

enum EmployeeType {
    HOURLY, FREELANCE
}


interface Employee {
    double getSalary();
    double getBonus();
    double getOvertimeSalary();
    int getTicketsCount();
    void updateBonus(double amount);
    EmployeeType getEmployeeType();

    String getLevel();
}
abstract class EmployeeBase implements Employee, Comparable<EmployeeBase> {
    String ID;
    String level;
    double rate;
    double totalBonus;
    public EmployeeBase(String ID, String level, double rate) {
        this.ID = ID;
        this.level = level;
        this.rate = rate;
        this.totalBonus = 0.0;
    }
    public abstract double getSalary();
    public abstract double getOvertimeSalary();
    public abstract int getTicketsCount();
    public double getBonus() {
        return totalBonus;
    }
    public abstract EmployeeType getEmployeeType();
    public void updateBonus(double amount) {
        this.totalBonus += amount;
    }
    public String getLevel() {
        return level;
    }    public int compareTo(EmployeeBase other) {
        return Comparator.comparing(EmployeeBase::getSalary).thenComparing(EmployeeBase::getLevel).reversed().compare(this, other);
    }
}

class HourlyEmployee extends EmployeeBase {
    double hours;
    double overtimeHours;
    double regularHours;

    public HourlyEmployee(String ID, String level, double hours, double hourlyRate) {
        super(ID, level, hourlyRate);
        this.hours=hours;
        this.overtimeHours = Math.max(0, hours-40);
        this.regularHours = hours-overtimeHours;
    }
    public double getSalary() {
        return (regularHours * rate + overtimeHours * rate * 1.5);
    }
    public double getOvertimeSalary() {
        return overtimeHours * rate * 1.5;
    }
    public int getTicketsCount() {
        return -1;
    }
    @Override
    public EmployeeType getEmployeeType() {
        return EmployeeType.HOURLY;
    }

    @Override
    public String toString() {
        //Employee ID: 157f3d Level: level10 Salary: 2390.72 Regular hours: 40.00 Overtime hours: 23.14
        return String.format("Employee ID: %s Level: %s Salary: %.2f Regular hours: %.2f Overtime hours: %.2f", ID, level, getSalary()+getBonus(), regularHours, overtimeHours);
    }
}

class FreelanceEmployee extends EmployeeBase {
    private final List<Double> ticketPoints;

    public FreelanceEmployee(String ID, String level, double ticketRate, List<Double> ticketPoints) {
        super(ID, level, ticketRate);
        this.ticketPoints = ticketPoints;
    }
    public double getTicketPointsSum() {
        return ticketPoints.stream().mapToDouble(i -> i).sum();
    }    public double getSalary() {
        return getTicketPointsSum() * rate;
    }
    public int getTicketsCount() {
        return ticketPoints.size();
    }
    @Override
    public EmployeeType getEmployeeType() {
        return EmployeeType.FREELANCE;
    }
    public double getOvertimeSalary() {
        return -1;
    }
    @Override
    public String toString() {
        //Employee ID: 596ed2 Level: level10 Salary: 1290.00 Tickets count: 9 Tickets points: 43
        return String.format("Employee ID: %s Level: %s Salary: %.2f Tickets count: %d Tickets points: %.0f", ID, level, getSalary()+getBonus(), ticketPoints.size(), getTicketPointsSum());
    }
}

abstract class BonusDecorator implements Employee {
    Employee employee;
    public BonusDecorator(Employee employee) {
        this.employee = employee;
    }
    @Override
    public double getOvertimeSalary() {
        return employee.getOvertimeSalary();
    }
    public EmployeeType getEmployeeType() {
        return employee.getEmployeeType();
    }
    @Override
    public int getTicketsCount() {
        return employee.getTicketsCount();
    }
    @Override
    public void updateBonus(double amount) {
        employee.updateBonus(amount);
    }
    @Override
    public String getLevel() {
        return employee.getLevel();
    }
    @Override
    public String toString() {
        return employee.toString() + String.format(" Bonus: %.2f", getBonus());
    }
}
class FixedBonusDecorator extends BonusDecorator {
    double fixedAmount;
    public FixedBonusDecorator(Employee employee, double fixedAmount) {
        super(employee);
        this.fixedAmount = fixedAmount;
        employee.updateBonus(fixedAmount);
    }
    @Override
    public double getSalary() {
        double salaryWithoutBonus = employee.getSalary();
        return salaryWithoutBonus + fixedAmount;
    }
    @Override
    public double getBonus() {
        return fixedAmount;
    }
}
class PecentageBonusDecorator extends BonusDecorator {
    double percent;
    double bonus;
    public PecentageBonusDecorator(Employee employee, double percent) {
        super(employee);
        this.percent = percent;
        this.bonus = employee.getSalary() / 100.0 * percent;
        employee.updateBonus(bonus);
    }
    @Override
    public double getSalary() {
        double salaryWithoutBonus = employee.getSalary();
        return salaryWithoutBonus + bonus;
    }
    @Override
    public double getBonus() {
        return bonus;
    }
}

class EmployeeFactory {
    public static Employee createEmployee(String line, Map<String, Double> hourlyRateByLevel, Map<String, Double> ticketRateByLevel) throws BonusNotAllowedException {
        String[] employeeParts = line.split("\\s+");
        Employee employee = createSimpleEmployee(employeeParts[0], hourlyRateByLevel, ticketRateByLevel);

        if(employeeParts.length > 1) {
            String bonus = employeeParts[1];
            if(bonus.contains("%")) { //percentage bonus
                double percentage = Double.parseDouble(bonus.substring(0, bonus.length()-1));
                if(percentage > 20)
                    throw new BonusNotAllowedException(bonus);
                employee = new PecentageBonusDecorator(employee, percentage);
            } else { //fixed bonus
                double bonusAmount = Double.parseDouble(bonus);
                if(bonusAmount > 1000)
                    throw new BonusNotAllowedException(bonus + "$");
                employee = new FixedBonusDecorator(employee, bonusAmount);
            }
        }
        return employee;
    }

    public static Employee createSimpleEmployee(String subline, Map<String, Double> hourlyRateByLevel, Map<String, Double> ticketRateByLevel) {
        String[] employeeParts = subline.split(";");
        String type = employeeParts[0];
        String ID = employeeParts[1];
        String level = employeeParts[2];
        if(type.equals("H")) {
            double hourlyRate = hourlyRateByLevel.get(level);
            double hours = Double.parseDouble(employeeParts[3]);
            return new HourlyEmployee(ID, level, hours, hourlyRate);
        } else {
            List<Double> ticketPoints = Arrays.stream(employeeParts).skip(3).map(Double::parseDouble).collect(Collectors.toList());
            double ticketRate = ticketRateByLevel.get(level);
            return new FreelanceEmployee(ID, level, ticketRate, ticketPoints);
        }
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
        Employee newEmployee = EmployeeFactory.createEmployee(line, hourlyRateByLevel, ticketRateByLevel);
        employees.add(newEmployee);
        return newEmployee;
    }
    public Map<String, Double> getOvertimeSalaryForLevels() {
//        return employees.stream()
//                .filter(employee ->  employee.getEmployeeType().equals(EmployeeType.HOURLY))
//                .collect(Collectors.groupingBy(
//                        Employee::getLevel,
//                        Collectors.summingDouble(Employee::getOvertimeSalary)
//                ));

        Map<String, Double> result = employees.stream().collect(Collectors.groupingBy(
                Employee::getLevel,
                Collectors.summingDouble(Employee::getOvertimeSalary)
        ));//
        List<String> keysWithZeros = result.keySet().stream().filter(key -> result.get(key) == -1).collect(Collectors.toList());
        keysWithZeros.forEach(result::remove);
        return result;
    }
    public Map<String, Integer> ticketsDoneByLevel() {
        return employees.stream()
                .filter(employee ->  employee.getEmployeeType().equals(EmployeeType.FREELANCE))
                .collect(Collectors.groupingBy(
                        Employee::getLevel,
                        Collectors.summingInt(Employee::getTicketsCount)
                ));
    }
    public void printStatisticsForOvertimeSalary() {
        DoubleSummaryStatistics dss = employees.stream()
                .filter(employee ->  employee.getEmployeeType().equals(EmployeeType.HOURLY))
                .mapToDouble(Employee::getOvertimeSalary)
                .summaryStatistics();
        System.out.printf("Statistics for overtime salary: Min: %.2f Average: %.2f Max: %.2f Sum: %.2f", dss.getMin(), dss.getAverage(), dss.getMax(), dss.getSum());
    }
    Collection<Employee> getFirstNEmployeesByBonus (int n) {
        return employees.stream()
                .sorted(Comparator.comparing(Employee::getBonus))
                .limit(n)
                .collect(Collectors.toList());
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
