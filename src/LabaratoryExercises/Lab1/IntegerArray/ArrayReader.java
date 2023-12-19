package LabaratoryExercises.Lab1.IntegerArray;

import java.io.InputStream;
import java.util.Scanner;

public class ArrayReader {
    public static IntegerArray readIntegerArray(InputStream input) {
        Scanner sc = new Scanner(input);
        int num = sc.nextInt();
        int[] tempArray = new int[num];
        for(int i=0; i<num; i++) {
            tempArray[i] = sc.nextInt();
        }
        return new IntegerArray(tempArray);
    }
}
