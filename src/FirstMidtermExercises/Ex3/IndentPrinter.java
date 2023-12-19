package FirstMidtermExercises.Ex3;


import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class IndentPrinter {
    public static String printIndent(int indentLevel) {
        return IntStream.range(0, indentLevel)
                .mapToObj(i -> "\t").collect(Collectors.joining());

    }
}
