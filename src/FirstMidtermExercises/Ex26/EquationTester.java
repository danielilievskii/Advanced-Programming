package FirstMidtermExercises.Ex26;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;


class Line {
    Double coeficient;
    Double x;
    Double intercept;

    public Line(Double coeficient, Double x, Double intercept) {
        this.coeficient = coeficient;
        this.x = x;
        this.intercept = intercept;
    }

    public static Line createLine(String line) {
        String[] parts = line.split("\\s+");
        return new Line(
                Double.parseDouble(parts[0]),
                Double.parseDouble(parts[1]),
                Double.parseDouble(parts[2])
        );
    }

    public double calculateLine() {
        return coeficient * x + intercept;
    }

    @Override
    public String toString() {
        return String.format("%.2f * %.2f + %.2f", coeficient, x, intercept);
    }
}

class Equation<IN, OUT> {
    Supplier<IN> supplier;
    Function<IN, OUT> function;

    public Equation(Supplier<IN> supplier, Function<IN, OUT> function) {
        this.supplier = supplier;
        this.function = function;
    }

    Optional<OUT> calculate() {
        return Optional.of(function.apply(supplier.get()));
    }
}

class EquationProcessor {
    public static <IN, OUT> void process(List<IN> inputs, List<Equation<IN, OUT>> equations) {

        inputs.forEach(input -> {
            System.out.println("Input: " + input.toString());
            equations.forEach(equation -> {
                Optional<OUT> result = equation.calculate();
                if(input.equals(inputs.get(inputs.size()-1))) {
                    //result.ifPresent(r -> System.out.printf("Result: %s\n", result.get()));
                    if(result.isPresent()) {
                        System.out.printf("Result: %s\n", result.get());
                    }
                }
                if (result.isEmpty()) {
                    System.out.println("An error happened");
                }

            });
        });
    }
}

public class EquationTester {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int testCase = Integer.parseInt(sc.nextLine());

        if (testCase == 1) { // Testing with Integer, Integer
            List<Equation<Integer, Integer>> equations1 = new ArrayList<>();
            List<Integer> inputs = new ArrayList<>();
            while (sc.hasNext()) {
                inputs.add(Integer.parseInt(sc.nextLine()));
            }

            // TODO: Add an equation where you get the 3rd integer from the inputs list, and the result is the sum of that number and the number 1000.
            equations1.add(new Equation<>(
                    () -> inputs.get(2),
                    integer -> Integer.sum(integer, 1000)
            ));

            // TODO: Add an equation where you get the 4th integer from the inputs list, and the result is the maximum of that number and the number 100.
            equations1.add(new Equation<>(
                    () -> inputs.get(4),
                    integer -> Integer.max(integer, 100)
            ));

            EquationProcessor.process(inputs, equations1);

        } else { // Testing with Line, Integer
            List<Equation<Line, Double>> equations2 = new ArrayList<>();
            List<Line> inputs = new ArrayList<>();
            while (sc.hasNext()) {
                inputs.add(Line.createLine(sc.nextLine()));
            }

            //TODO Add an equation where you get the 2nd line, and the result is the value of y in the line equation.
            equations2.add(new Equation<>(
                    () -> inputs.get(1),
                    Line::calculateLine

            ));

            //TODO Add an equation where you get the 1st line, and the result is the sum of all y values for all lines that have a greater y value than that equation.
            equations2.add(new Equation<>(
                    () -> inputs.get(0),
                    line -> inputs.stream().filter(i -> i.calculateLine()> line.calculateLine()).mapToDouble(i -> i.calculateLine()).sum()
            ));

            EquationProcessor.process(inputs, equations2);
        }
    }
}

