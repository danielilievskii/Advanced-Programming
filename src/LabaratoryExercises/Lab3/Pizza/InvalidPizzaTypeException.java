package LabaratoryExercises.Lab3.Pizza;

public class InvalidPizzaTypeException extends Exception{
    InvalidPizzaTypeException(String message) {
        super("Invalid pizza type exception");
    }
}
