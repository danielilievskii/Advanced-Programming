package LabaratoryExercises.Lab2.Matrix;

public class InvalidColumnNumberException extends Exception {
    InvalidColumnNumberException() {
        super(String.format("Invalid column number"));
    }
}
