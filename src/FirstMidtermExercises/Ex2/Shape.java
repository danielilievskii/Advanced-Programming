package FirstMidtermExercises.Ex2;

public abstract class Shape implements Comparable<Shape>{
    int size;

    public Shape(int size) {
        this.size=size;
    }

    public abstract double getArea();
    public abstract TYPE getType();

    @Override
    public int compareTo(Shape other) {
        return Double.compare(getArea(), other.getArea());
    }

}

