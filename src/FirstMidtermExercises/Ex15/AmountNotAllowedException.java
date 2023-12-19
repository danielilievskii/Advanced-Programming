package FirstMidtermExercises.Ex15;

public class AmountNotAllowedException extends Exception{
    public AmountNotAllowedException(int amount) {
        super(String.format("Receipt with amount %d is not allowed to be scanned", amount));
    }
}
