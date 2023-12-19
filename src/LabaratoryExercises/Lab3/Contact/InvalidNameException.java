package LabaratoryExercises.Lab3.Contact;

public class InvalidNameException extends Exception{
    String name;
    InvalidNameException(String name) {
        super();
        this.name = name;
    }
}
