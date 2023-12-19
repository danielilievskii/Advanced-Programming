package FirstMidtermExercises.Ex2;

public class CircleShape extends Shape {
    int size;

    public CircleShape(int size) {
        super(size);
    }

    @Override
    public double getArea() {
        return size*size*Math.PI;
    }

    @Override
    public TYPE getType() {
        return TYPE.CIRCLE;
    }
}