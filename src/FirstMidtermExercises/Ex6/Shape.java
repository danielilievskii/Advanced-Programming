package FirstMidtermExercises.Ex6;

public abstract class Shape implements Scalable, Stackable, Comparable<Shape>{

    private String ID;
    private Color color;

    public Shape(String ID, Color color) {
        this.ID=ID;
        this.color=color;
    }

    public abstract TYPE getType();

    public String getID() {
     return ID;
    }

    public Color getColor() {
        return color;
    }

    public int compareTo(Shape o) {
        return Float.compare(this.weight(), o.weight());
    }


}
