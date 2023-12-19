package LabaratoryExercises.Lab2.Matrix;

public class InvalidRowNumberException extends Exception{
    InvalidRowNumberException() {
        super(String.format("Invalid row number"));
    }

}
