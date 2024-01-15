package SecondMidtermExercises.Ex08_Canvas;
import java.io.*;
import java.util.*;

interface Shape {
    public abstract double getArea();
    public abstract double getPerimeter();
    public abstract void scale(double coef);
}

class Circle implements Shape {
    private double radius;
    public Circle(double radius) {
        this.radius = radius;
    }

    @Override
    public double getArea() {
        return radius*radius*Math.PI;
    }
    @Override
    public double getPerimeter() {
        return 2*radius*Math.PI;
    }
    @Override
    public void scale(double coef) {
        radius *= coef;
    }

    @Override
    public String toString() {
        //Circle -> Radius: 4.88 Area: 74.92 Perimeter: 30.68
        return String.format("Circle -> Radius: %.2f Area: %.2f Perimeter: %.2f", radius, getArea(), getPerimeter());
    }
}

class Square implements Shape {
    private double side;

    public Square(double side) {
        this.side = side;
    }

    @Override
    public double getArea() {
        return side*side;
    }
    @Override
    public double getPerimeter() {
        return 4*side;
    }
    @Override
    public void scale(double coef) {
        side *= coef;
    }
    @Override
    public String toString() {
        return String.format("Square: -> Side: %.2f Area: %.2f Perimeter: %.2f", side, getArea(), getPerimeter());
    }
}
class Rectangle implements Shape {
    private double sideA;
    private double sideB;
    public Rectangle(double sideA, double sideB) {
        this.sideA = sideA;
        this.sideB = sideB;
    }
    @Override
    public double getArea() {
        return sideA*sideB;
    }
    @Override
    public double getPerimeter() {
        return 2*sideA + 2*sideB;
    }
    @Override
    public void scale(double coef) {
        sideA *= coef;
        sideB *= coef;
    }

    @Override
    public String toString() {
        //Rectangle: -> Sides: 6.80, 7.63 Area: 51.86 Perimeter: 28.85
        return String.format("Rectangle: -> Sides: %.2f, %.2f Area: %.2f Perimeter: %.2f", sideA, sideB, getArea(), getPerimeter());
    }
}

class InvalidIDException extends Exception {
    public InvalidIDException(String msg) {
        super(msg);
    }
}

class InvalidDimensionException extends Exception {
    public InvalidDimensionException(String msg) {
        super(msg);
    }
}

class ShapeFactory {
    public static boolean checkUserID(String userID) {
//        String regexPattern = "^[A-Za-z0-9]+$";
//        return userID.matches(regexPattern) && userID.length() == 6;

        if (userID.length() != 6)
            return false;

        for (char c : userID.toCharArray()) {
            if (!Character.isLetterOrDigit(c))
                return false;
        }
        return true;
    }

    public static Shape createShape(String line) throws InvalidIDException, InvalidDimensionException {
        String[] parts = line.split("\\s+");
        int type  = Integer.parseInt(parts[0]);
        double firstDimension = Double.parseDouble(parts[2]);
        if(firstDimension == 0.0) {
            throw new InvalidDimensionException("Dimension 0 is not allowed!");
        }
        if(type==1) {
            return new Circle(firstDimension);
        } else if (type==2) {
            return new Square(firstDimension);
        } else {
            double secondDimension = Double.parseDouble(parts[3]);
            if(secondDimension == 0.0) {
                throw new InvalidDimensionException("Dimension 0 is not allowed!");
            }
            return new Rectangle(firstDimension, secondDimension);
        }
    }

    public static String extractUserID(String line) throws InvalidIDException {
        String[] parts = line.split("\\s+");
        String userID = parts[1];
        if(!checkUserID(userID))
            throw new InvalidIDException(String.format("ID %s is not valid", userID));
        return userID;
    }
}

class Canvas{

    Map<String, Set<Shape>> shapes;
    Set<Shape> allShapes;

    public Canvas() {
        shapes = new TreeMap<>();
        allShapes = new TreeSet<>(Comparator.comparing(Shape::getArea)); //for printAllShapes method -> sorted() isn't allowed AND for statistics :(

    }
    void readShapes (InputStream is) throws InvalidDimensionException {
        Scanner sc = new Scanner(is);
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            try {
                Shape newShape = ShapeFactory.createShape(line);
                String userID = ShapeFactory.extractUserID(line);
                shapes.putIfAbsent(userID, new TreeSet<>(Comparator.comparing(Shape::getPerimeter)));
                shapes.get(userID).add(newShape);
                allShapes.add(newShape);
            } catch (InvalidIDException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    void scaleShapes (String userID, double coef) {
        shapes.getOrDefault(userID, new HashSet<>()).forEach(shape -> shape.scale(coef));
    }

    void printAllShapes (OutputStream os) {
        PrintWriter pw = new PrintWriter(os);
        allShapes.forEach(pw::println);
        pw.flush();
    }

    void printByUserId (OutputStream os) {
        PrintWriter pw = new PrintWriter(os);

        Comparator<Map.Entry<String, Set<Shape>>> entryComparator = Comparator.comparing(entry -> entry.getValue().size());

        shapes.entrySet().stream()
                .sorted(entryComparator.reversed().thenComparing(entry -> entry.getValue().stream().mapToDouble(Shape::getPerimeter).sum()))
                .forEach(entry -> {
                    pw.println(String.format("Shapes of user: %s", entry.getKey()));
                    entry.getValue().forEach(pw::println);
        });
        pw.flush();
    }

    void statistics (OutputStream os) {
        PrintWriter pw = new PrintWriter(os);
        DoubleSummaryStatistics dss = allShapes.stream().mapToDouble(Shape::getArea).summaryStatistics();
        pw.println(String.format("count: %d\nsum: %.2f\nmin: %.2f\naverage: %.2f\nmax: %.2f\n", dss.getCount(), dss.getSum(), dss.getMin(), dss.getAverage(), dss.getMax()));
        pw.flush();
    }

}

public class CanvasTest {

    public static void main(String[] args)  {
        Canvas canvas = new Canvas();

        System.out.println("READ SHAPES AND EXCEPTIONS TESTING");
        try {
            canvas.readShapes(System.in);
        } catch (InvalidDimensionException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("BEFORE SCALING");
        canvas.printAllShapes(System.out);
        canvas.scaleShapes("123456", 1.5);
        System.out.println("AFTER SCALING");
        canvas.printAllShapes(System.out);

        System.out.println("PRINT BY USER ID TESTING");
        canvas.printByUserId(System.out);

        System.out.println("PRINT STATISTICS");
        canvas.statistics(System.out);
    }
}
