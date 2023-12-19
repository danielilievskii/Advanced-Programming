package FirstMidtermExercises.Ex1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Canvas implements Comparable<Canvas>{
    private String ID;
    private List<Integer> size;

    public Canvas(String str) {
        this.size = new ArrayList<>();

        String[] split = str.split("\\s+");
        this.ID = split[0];
//        for(int i=1; i<split.length; i++) {
//            size.add(Integer.parseInt(split[i]));
//        }

        size = Arrays.stream(split).skip(1).map(size -> Integer.parseInt(size)).collect(Collectors.toList());
    }

    int totalSquares() {
        return size.size();
    }

    int getPerimeter() {
        int sum=0;
        for(Integer s:size) {
            sum+=4*s;
        }
        return sum;
    }

    @Override
    public String toString() {
        return String.format("%s %d %d", this.ID, this.size.size(), this.getPerimeter());
    }

    @Override
    public int compareTo(Canvas other) {

        return Integer.compare(this.getPerimeter(), other.getPerimeter());
    }
}
