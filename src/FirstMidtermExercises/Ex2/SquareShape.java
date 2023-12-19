package FirstMidtermExercises.Ex2;

public class SquareShape extends Shape {
    int size;

    public SquareShape(int size) {
        super(size);
    }

    @Override
    public double getArea() {
        return size*size;
    }

    @Override
    public TYPE getType() {
        return TYPE.SQUARE;
    }
}