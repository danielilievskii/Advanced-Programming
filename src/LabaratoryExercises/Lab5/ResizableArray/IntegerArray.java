package LabaratoryExercises.Lab5.ResizableArray;

import java.util.Arrays;
import java.util.stream.IntStream;

public class IntegerArray extends ResizableArray<Integer>{


    public double sum() {
        double sum=0;
        for(int i=0; i<count(); i++) {
            sum=sum+ elementAt(i);
        }
        return sum;
    }

    public double mean() {
        return sum()/count();
    }

    public int countNonZero() {
        int count=0;
        for(int i=0; i<count(); i++) {
            if(elementAt(i)!=0) {
                count++;
            }
        }
        return count;
    }

    public IntegerArray distinct() {
        IntegerArray arr = new IntegerArray();
        for(int i=0; i<count(); i++) {
            if(!arr.contains(elementAt(i))) {
                arr.addElement(elementAt(i));
            }
        }
        return arr;
    }

    public IntegerArray increment(int offset) {
        IntegerArray arr = new IntegerArray();
        for(int i=0; i<count(); i++) {
            arr.addElement(elementAt(i) + offset);
        }
        return arr;
    }

}
