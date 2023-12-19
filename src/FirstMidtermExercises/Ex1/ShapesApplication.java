package FirstMidtermExercises.Ex1;


import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ShapesApplication {
    List<Canvas> shapes;

    ShapesApplication() {
        this.shapes=new ArrayList<>();
    }

//    int readCanvases(InputStream inputStream) {
//        Scanner sc = new Scanner(inputStream);
//        String str;
//        int counter=0;
//
//        while(sc.hasNextLine()) {
//            str = sc.nextLine();
//            counter+= str.split("\\s+").length-1;
//            shapes.add(new Canvas(str));
//        }
//        return counter;
//    }

    int readCanvases(InputStream inputStream) {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        shapes = br.lines()
                .map(line -> new Canvas(line))
                .collect(Collectors.toList());

        return shapes.stream().mapToInt(canvas -> canvas.totalSquares()).sum();
    }

    void printLargestCanvasTo (OutputStream outputStream) {
        PrintWriter pw = new PrintWriter(outputStream);

        pw.println(shapes.stream().max(Comparator.naturalOrder()).get());
        pw.flush();
    }

}
