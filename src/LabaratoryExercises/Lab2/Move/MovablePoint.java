package LabaratoryExercises.Lab2.Move;

public class MovablePoint implements Movable{
    private int x;
    private int y;
    private final int xSpeed;
    private final int ySpeed;

    public MovablePoint(int x, int y, int xSpeed, int ySpeed) {
        this.x = x;
        this.y = y;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }

    public void tryMoving(int x, int y) throws ObjectCanNotBeMovedException {
        if(this.x + x > MovablesCollection.xMAX || this.x + x < 0 || this.y + y > MovablesCollection.yMAX || this.y + y < 0) {
            throw new ObjectCanNotBeMovedException(String.format("Point (%d,%d) is out of bounds", this.x + x, this.y + y));
        }
    }

    @Override
    public void moveUp() throws ObjectCanNotBeMovedException {
        tryMoving(0, ySpeed);
        this.y+=this.ySpeed;
    }
    @Override
    public void moveDown() throws ObjectCanNotBeMovedException {
        tryMoving(0, -ySpeed);
        this.y-=this.ySpeed;
    }

    @Override
    public void moveLeft() throws ObjectCanNotBeMovedException {
        tryMoving(-xSpeed, 0);
        this.x-=xSpeed;
    }

    @Override
    public void moveRight() throws ObjectCanNotBeMovedException {
        tryMoving(xSpeed, 0);
        this.x+=xSpeed;
    }

    public int getCurrentYPosition() {
        return y;
    }

    public int getCurrentXPosition() {
        return x;
    }

    @Override
    public String toString() {
        return String.format("Movable point with coordinates (%d,%d)", x, y);
    }

    public TYPE getType() {
        return TYPE.POINT;
    }
}
