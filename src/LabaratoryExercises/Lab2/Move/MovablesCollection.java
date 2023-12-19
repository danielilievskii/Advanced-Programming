package LabaratoryExercises.Lab2.Move;

import java.util.ArrayList;
import java.util.Arrays;

public class MovablesCollection {
    private Movable[] movable;
    static int xMAX = 0;
    static int yMAX = 0;

    MovablesCollection(int x_MAX, int y_MAX) {
        movable = new Movable[0];
        xMAX = x_MAX;
        yMAX = y_MAX;
    }

    public boolean canFit(Movable m) {
        int x = m.getCurrentXPosition();
        int y = m.getCurrentYPosition();
        int r = 0;

        if(m.getType() == TYPE.CIRCLE) {
            r = ((MovableCircle) m).getRadius();
        }

        return x-r >= 0 && x+r <= xMAX && y-r >= 0 && y+r <= yMAX;
    }

    public void addMovableObject(Movable m) throws MovableObjectNotFittableException{
        if(!canFit(m)) {
            if(m.getType() == TYPE.POINT) {
                throw new MovableObjectNotFittableException(String.format("Movable point with center (%d,%d) can not be fitted into the collection", m.getCurrentXPosition(), m.getCurrentYPosition()));
            } else if(m.getType() == TYPE.CIRCLE) {
                throw new MovableObjectNotFittableException(String.format("Movable circle with center (%d,%d) and radius %d can not be fitted into the collection", m.getCurrentXPosition(), m.getCurrentYPosition(), ((MovableCircle) m).getRadius()));
            }
        } else {
            this.movable = Arrays.copyOf(movable, movable.length+1);
            this.movable[this.movable.length-1] = m;
        }
    }

    public void moveObjectsFromTypeWithDirection (TYPE type, DIRECTION direction) throws ObjectCanNotBeMovedException {
        for(Movable m: movable) {
            if(m.getType() == type) {
                if(direction == DIRECTION.UP) {
                    m.moveUp();
                } else if(direction == DIRECTION.DOWN) {
                    m.moveDown();
                }
                else if(direction == DIRECTION.LEFT) {
                    m.moveLeft();
                }
                else if(direction == DIRECTION.RIGHT) {
                    m.moveRight();
                }
            }
        }
    }

    public static void setxMax(int x) {
        xMAX = x;
    }

    public static void setyMax(int y) {
        yMAX=y;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb
                .append("Collection of movable objects with size ")
                .append(this.movable.length)
                .append(":\n");
        for(Movable m: movable) {
            sb
                    .append(m)
                    .append("\n");
        }
        return sb.toString();
    }
}
