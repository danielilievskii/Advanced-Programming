package FirstMidtermExercises.Ex2;


import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class ShapesApplication {
    private double maxArea;
    List<Canvas> canvas;

    public ShapesApplication(double maxArea) {
        this.maxArea=maxArea;
        this.canvas=new ArrayList<>();
    }

    void addCanvas(Canvas c) throws IrregularCanvasException{
        if(c.getMax() > maxArea) {
            throw new IrregularCanvasException(c.getID(), maxArea);
        } else canvas.add(c);
    }

    void readCanvases(InputStream inputStream) {
//        Scanner sc = new Scanner(inputStream);
//        String str;
//
//        while(sc.hasNextLine()) {
//            try {
//                addCanvas(new Canvas(sc.nextLine()));
//            } catch(IrregularCanvasException e) {
//                System.out.println(e.getMessage());
//            }
//
//        }
        this.canvas=new ArrayList<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        br.lines().forEach(line -> {
            try {
                addCanvas(new Canvas(line));
            } catch (IrregularCanvasException e) {
                System.out.println(e.getMessage());
            }
        });

    }


    void printCanvases(OutputStream os) {
        PrintWriter pw = new PrintWriter(os);
        canvas.stream().sorted(Comparator.reverseOrder()).forEach(pw::println);
        pw.flush();
    }
}
