package LabaratoryExercises.Lab1.IntegerArray;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Scanner;



// IntegerArray must be immutable
public final class IntegerArray {

    private int[] array;
    public IntegerArray(int[] array) {
        this.array = Arrays.copyOf(array, array.length);
    }

    public int length() {
        return this.array.length;
    }

    public int getElementAt(int index) {
        if(index < 0 || index >= array.length)
            throw new ArrayIndexOutOfBoundsException();
        return array[index];
    }

    public int sum() {
        return Arrays.stream(this.array).sum();
    }

    public double average() {
        //return Arrays.stream(this.array).average().getAsDouble();
        return (double) sum()/length();
    }

    public IntegerArray getSorted() {
        return new IntegerArray(Arrays.stream(this.array).sorted().toArray());
    }

    public IntegerArray concat(IntegerArray ia) {
        int[] concatArray = new int[this.length() + ia.length()];

        System.arraycopy(this.array, 0, concatArray, 0, this.length());
        System.arraycopy(ia.array, 0, concatArray, this.length(), ia.length());

//        for (int i=0; i<this.length(); i++) {
//            concatArray[i]=this.array[i];
//        }
//        for (int i=this.length(); i<this.length() + ia.length(); i++) {
//            concatArray[i] = ia.array[i-this.length()];
//        }

        return new IntegerArray(concatArray);
    }


    @Override
    public String toString() {
        //return Arrays.toString(array);

        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for(int i=0; i<length() - 1; i++) {
            sb.append(array[i]).append(", ");
        }
        sb.append(array[length()-1]).append("]");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IntegerArray that = (IntegerArray) o;
        return Arrays.equals(array, that.array);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(array);
    }
}
