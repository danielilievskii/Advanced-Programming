package FirstMidtermExercises.Ex6;

import java.util.*;
import java.util.stream.Collectors;

public class Canvas {

    List<Shape> shapes;

    public Canvas() {
        this.shapes=new ArrayList<>();
    }

//    void add(String id, Color color, float radius) {
//        CircleShape toAdd = new CircleShape(id, color, radius);
//        int index = binarySearchForInsertionPoint(toAdd.weight());
//        shapes.add(index, toAdd);
//    }
//
//    void add(String id, Color color, float width, float height) {
//        SquareShape toAdd = new SquareShape(id, color, width, height);
//        int index = binarySearchForInsertionPoint(toAdd.weight());
//        shapes.add(index, toAdd);
//    }
//
//    private int binarySearchForInsertionPoint(float weight) {
//        int left = 0;
//        int right = shapes.size() - 1;
//
//        while (left <= right) {
//            int mid = left + (right - left) / 2;
//            float midWeight = shapes.get(mid).weight();
//
//            if (midWeight == weight) {
//                return mid; // Insert at this position to maintain order.
//            } else if (midWeight > weight) {
//                left = mid + 1;
//            } else {
//                right = mid - 1;
//            }
//        }
//
//        return left; // Insert at the correct position.
//    }

    int findIndex(float weight) {
        int index=-1;
        for(int i=shapes.size()-1; i>=0; i--) {
            if(shapes.get(i).weight()>=weight) {
                index=i;
                break;
            }
        }
        return index;
    }

    void add(String id, Color color, float radius) {
        CircleShape toAdd = new CircleShape(id, color, radius);
        if(!shapes.isEmpty()) {
            shapes.add(findIndex(toAdd.weight())+1, toAdd);
        } else shapes.add(toAdd);

//        shapes.add(toAdd);
//        shapes=shapes.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());

    }

    void add(String id, Color color, float width, float height) {
        SquareShape toAdd = new SquareShape(id, color, width, height);
        if(!shapes.isEmpty()) {
            shapes.add(findIndex(toAdd.weight())+1, toAdd);
        } else shapes.add(toAdd);

//        shapes.add(toAdd);
//        shapes=shapes.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());

    }

    public void scale(String id, float scaleFactor) {
        for (int i = 0; i < shapes.size(); i++) {
            Shape s = shapes.get(i);
            if (s.getID().equals(id)) {
                shapes.remove(i);
                s.scale(scaleFactor);
                int index = findIndex(s.weight());
                shapes.add(index+1, s);
                break;
            }
        }
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Shape s: shapes) {
            if(s.getType()==TYPE.CIRCLE) {
                sb.append(String.format("C: %-4s %-9s %10.2f%n", s.getID(), s.getColor(), s.weight()));
            } else if(s.getType()==TYPE.SQUARE) {
                sb.append(String.format("R: %-4s %-9s %10.2f%n", s.getID(), s.getColor(),  s.weight()));
            }
        }

        return sb.toString();
    }
}
