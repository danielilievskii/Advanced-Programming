package LabaratoryExercises.Lab2.Move;

public interface Movable {
    public void moveUp() throws ObjectCanNotBeMovedException;
    public void moveLeft() throws ObjectCanNotBeMovedException;
    public void moveRight() throws ObjectCanNotBeMovedException;
    public void moveDown() throws ObjectCanNotBeMovedException;
    public int getCurrentXPosition();
    public int getCurrentYPosition();

    public TYPE getType();
}
