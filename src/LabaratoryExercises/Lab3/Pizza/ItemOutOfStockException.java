package LabaratoryExercises.Lab3.Pizza;

public class ItemOutOfStockException extends Exception{
    ItemOutOfStockException(Item item) {
        super("the item is out of stock.");
    }
}
