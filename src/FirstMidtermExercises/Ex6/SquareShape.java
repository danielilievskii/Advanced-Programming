package FirstMidtermExercises.Ex6;

public class SquareShape extends Shape {
    private float width;
    private float height;
    public SquareShape(String id, Color color, float width, float height) {
        super(id, color);
        this.width=width;
        this.height=height;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    @Override
    public void scale(float scaleFactor) {
        this.width=this.width*scaleFactor;
        this.height=this.height*scaleFactor;
    }

    public TYPE getType() {
        return TYPE.SQUARE;
    }

    @Override
    public float weight() {
        return width*height;
    }


}
