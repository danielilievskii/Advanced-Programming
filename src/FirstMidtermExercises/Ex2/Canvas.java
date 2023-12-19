package FirstMidtermExercises.Ex2;
import javax.swing.text.html.parser.Parser;
import java.sql.ClientInfoStatus;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Canvas implements Comparable<Canvas> {
    String ID;
    private List<Shape> shapes;

    public Canvas(String line) {
        this.shapes = new ArrayList<>();

        String[] parts = line.split("\\s+");
       this.ID = parts[0];
        for(int i=1; i<parts.length; i+=2) {
            if(parts[i].equals("C"))
                shapes.add(new CircleShape(Integer.parseInt(parts[i+1])));
            else if(parts[i].equals("S"))
                shapes.add(new SquareShape(Integer.parseInt(parts[i+1])));
        }
    }

    public double getMax() {
        return Collections.max(shapes).getArea();

        //return shapes.stream().max(Comparator.naturalOrder()).get().getArea();
    }

    public double getMin() {
        return Collections.min(shapes).getArea();
        //return shapes.stream().mapToDouble(Shape::getArea).min().getAsDouble();
    }

    public double getAreaCanvas() {
        return shapes.stream().mapToDouble(Shape::getArea).sum();
    }

    public double getAverageAreaCanvas() {
        return shapes.stream().mapToDouble(Shape::getArea).average().getAsDouble();
    }

    public long totalCircles() {
        return shapes.stream().filter(i-> i.getType()==TYPE.CIRCLE).count();
    }

    public long totalSquares() {
        return shapes.stream().filter(i -> i.getType()==TYPE.SQUARE).count();
    }

    @Override
    public String toString() {

        return String.format("%s %d %d %d %.2f %.2f %.2f", ID, shapes.size(), totalCircles(), totalSquares(), getMin(), getMax(), getAverageAreaCanvas());
    }

    public String getID() {
        return ID;
    }

    @Override
    public int compareTo(Canvas o) {
        return Double.compare(getAreaCanvas(), o.getAreaCanvas());
    }
}

