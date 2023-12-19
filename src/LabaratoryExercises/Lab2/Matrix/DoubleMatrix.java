package LabaratoryExercises.Lab2.Matrix;

import java.util.Arrays;
import java.util.Objects;
public class DoubleMatrix {
    private double[][] matrix;
    private int m;
    private int n;
    DoubleMatrix(double[] array, int m, int n) throws InsufficientElementsException {
        this.m=m;
        this.n=n;
        if(array.length < m*n) {
            throw new InsufficientElementsException("Insufficient number of elements");
        } else if(array.length == m*n) {
            this.matrix = new double[m][n];
            int counter=0;
            for(int i=0; i<m; i++) {
                for(int j=0; j<n; j++) {
                    this.matrix[i][j]=array[counter++];
                }
            }
        } else {
            this.matrix = new double[m][n];
            int counter = array.length - m*n;
            for(int i=0; i<m; i++) {
                for(int j=0; j<n; j++) {
                    this.matrix[i][j]=array[counter++];
                }
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DoubleMatrix that = (DoubleMatrix) o;
        return m == that.m && n == that.n && Arrays.deepEquals(matrix, that.matrix);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(m, n);
        result = 31 * result + Arrays.deepHashCode(matrix);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for(int i=0; i<m; i++) {
            for (int j=0; j<n; j++) {
                sb.append(String.format("%.2f", matrix[i][j]));

                if(j!=n-1)
                    sb.append("\t");
            }
            if(i!=m-1)
                sb.append("\n");
        }


        return sb.toString();
    }

    public String getDimensions() {
        return String.format("[%d x %d]", m, n);
    }
    public int rows() {
        return m;
    }
    public int columns() {
        return n;
    }
    public double maxElementAtRow(int row) throws InvalidRowNumberException {
        if(row<1 || row>m) {
            throw new InvalidRowNumberException();
        } else {
            //Solution 1
            return Arrays.stream(matrix[row-1]).max().getAsDouble();

            //Solution 2
//            double max=matrix[row-1][0];
//            for(int j=0; j<n; j++) {
//                if(matrix[row-1][j]>max) {
//                    max=matrix[row-1][j];
//                }
//            }
//            return max;
        }
    }

    public double maxElementAtColumn(int column) throws InvalidColumnNumberException {
        if(column<1 || column>n) {
            throw new InvalidColumnNumberException();
        } else {
            //Solution 1
            double[] col = new double[m];
            for(int i=0; i<m; i++) {
                col[i]=matrix[i][column-1];
            }
            return Arrays.stream(col).max().getAsDouble();

            //Solution 2
//            double max=matrix[0][column-1];
//            for(int i=0; i<n; i++) {
//                if(matrix[i][column-1]>max) {
//                    max=matrix[i][column-1];
//                }
//            }
//            return max;
        }
    }

    public double sum() {
        double sum=0;
        for(int i=0; i<m; i++) {
            for(int j=0; j<n; j++) {
                sum+=matrix[m][n];
            }
        }
        return sum;
    }

    public double[] toSortedArray() {
        double[] array = new double[m*n];
        int counter=0;

        for(int i=0; i<m; i++) {
            for(int j=0; j<n; j++) {
                array[counter++] = matrix[i][j];

            }
        }

        Arrays.sort(array);
        for(int i=0; i<array.length/2; i++) {
            double tmp = array[i];
            array[i] = array[array.length-1-i];
            array[array.length-1-i]=tmp;
        }

        return array;
    }
}
