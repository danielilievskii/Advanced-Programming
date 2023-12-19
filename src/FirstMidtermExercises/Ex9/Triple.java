package FirstMidtermExercises.Ex9;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class  Triple<T extends Number> {
    private List<T> list;

    public Triple(T num1, T num2, T num3) {
        this.list=new ArrayList<>();

        this.list.add(num1);
        this.list.add(num2);
        this.list.add(num3);
    }

    public double max() {
        return this.list.stream().mapToDouble(t -> t.doubleValue()).max().orElse(0);
    }

    public double average() {
        return this.list.stream().mapToDouble(Number::doubleValue).average().getAsDouble();
    }

    void sort() {
        list = list.stream().sorted().collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return String.format("%.2f %.2f %.2f", list.get(0).doubleValue(), list.get(1).doubleValue(), list.get(2).doubleValue());
    }
}
