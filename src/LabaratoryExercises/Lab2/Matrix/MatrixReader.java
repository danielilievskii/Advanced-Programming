package LabaratoryExercises.Lab2.Matrix;

import java.io.InputStream;
import java.util.Scanner;
public class MatrixReader {
    public static DoubleMatrix read(InputStream input) throws InsufficientElementsException {
        Scanner sc = new Scanner(input);

        int m = sc.nextInt();
        int n = sc.nextInt();
        double[] matrix = new double[m*n];
        for(int i=0; i<m*n; i++) {
            matrix[i]=sc.nextDouble();
        }
        return new DoubleMatrix(matrix, m, n);
    }
}
