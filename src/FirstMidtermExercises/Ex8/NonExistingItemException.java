package FirstMidtermExercises.Ex8;

public class NonExistingItemException extends Exception{
    public NonExistingItemException(int id) {
        super(String.format("Item with id %d doesn't exist", id));
    }
}
