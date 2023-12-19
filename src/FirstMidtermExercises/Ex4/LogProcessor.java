package FirstMidtermExercises.Ex4;


import java.util.ArrayList;

public interface LogProcessor<E extends ILog & Comparable<E>> {
        ArrayList<E> processLogs(ArrayList<E>logs);
        }