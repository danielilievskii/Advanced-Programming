package FirstMidtermExercises.Ex2;

public class IrregularCanvasException extends Exception{
    public IrregularCanvasException(String ID, double maxArea) {
        super(String.format("Canvas %s has a shape with larger area than %.2f", ID, maxArea));
    }
}
