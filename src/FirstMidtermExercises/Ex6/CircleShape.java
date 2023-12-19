package FirstMidtermExercises.Ex6;

public class CircleShape extends Shape {
    private float radius;

    public CircleShape(String id, Color color, float radius) {
        super(id, color);
        this.radius=radius;
    }


    public void scale(float scaleFactor) {
        this.radius=this.radius*scaleFactor;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public float weight() {
        return radius*radius*(float)Math.PI;
    }

    public TYPE getType() {
        return TYPE.CIRCLE;
    }

}
