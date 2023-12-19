package LabaratoryExercises.Lab2.Move;

public class MovableCircle implements Movable {
    public int radius;
    public MovablePoint center;

    public MovableCircle(int radius, MovablePoint center) {
        this.radius = radius;
        this.center = center;
    }

    @Override
    public void moveUp() throws ObjectCanNotBeMovedException {
        center.moveUp();
    }

    @Override
    public void moveLeft() throws ObjectCanNotBeMovedException {
        center.moveLeft();
    }

    @Override
    public void moveRight() throws ObjectCanNotBeMovedException {
        center.moveRight();
    }

    @Override
    public void moveDown() throws ObjectCanNotBeMovedException {
        center.moveDown();
    }

    public int getCurrentXPosition() {
        return center.getCurrentXPosition();
    }

    public int getCurrentYPosition() {
        return center.getCurrentYPosition();
    }

    public int getRadius() {
        return radius;
    }

    @Override
    public String toString() {
        return String.format("Movable circle with center coordinates (%d,%d) and radius %d", center.getCurrentXPosition(), center.getCurrentYPosition(), radius);
    }

    public TYPE getType() {
        return TYPE.CIRCLE;
    }
}
