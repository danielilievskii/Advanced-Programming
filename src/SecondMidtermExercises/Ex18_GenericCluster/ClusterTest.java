package SecondMidtermExercises.Ex18_GenericCluster;
import java.util.*;

/**
 * January 2016 Exam problem 2
 */
interface Clusterable<T> {
    long id();
    double distance(T item);
}
class Point2D implements Clusterable<Point2D> {
    long id;
    float x;
    float y;

    public Point2D(long id, float x, float y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }
    @Override
    public long id() {
        return id;
    }
    @Override
    public double distance(Point2D item) {
        return Math.sqrt(Math.pow(x - item.x, 2) + Math.pow(y - item.y, 2));
    }
}

class Cluster<T extends Clusterable<T>> {
    Map<Long, T> cluster;

    public Cluster() {
        this.cluster = new HashMap<>();
    }
    public void addItem(T element) {
        cluster.put(element.id(), element);
    }

    public void near(long id, int top) {
        final T element = cluster.get(id);
        List<T> allElements = new ArrayList<>(cluster.values());
        allElements.sort(Comparator.comparing(el -> el.distance(element)));
        for(int i=1; i<=top && i < allElements.size(); ++i) {
            T el = allElements.get(i);
            System.out.println(String.format("%d. %d -> %.3f", i, el.id(), el.distance(element)));
        }
    }
}
public class ClusterTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Cluster<Point2D> cluster = new Cluster<>();
        int n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(" ");
            long id = Long.parseLong(parts[0]);
            float x = Float.parseFloat(parts[1]);
            float y = Float.parseFloat(parts[2]);
            cluster.addItem(new Point2D(id, x, y));
        }
        int id = scanner.nextInt();
        int top = scanner.nextInt();
        cluster.near(id, top);
        scanner.close();
    }
}

