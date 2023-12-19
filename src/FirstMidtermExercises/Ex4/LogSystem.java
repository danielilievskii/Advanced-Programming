package FirstMidtermExercises.Ex4;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class LogSystem<E extends ILog & Comparable<E>> {
        //TODO add instance variable(s)
        ArrayList<E> logsList;
        //TODO constructor
    public LogSystem(ArrayList<E> logs) {
        this.logsList = logs;
    }

    void printResults() {

        //TODO define concrete log processors with lambda expressions
        LogProcessor<E> firstLogProcessor = logList -> logList.stream().filter(i -> i.getType().equals("INFO")).collect(Collectors.toCollection(ArrayList::new));

        LogProcessor<E> secondLogProcessor = logList -> logList.stream().filter(i -> i.getMessage().length() <=100).collect(Collectors.toCollection(ArrayList::new));

        LogProcessor<E>thirdLogProcessor = logList -> logList.stream().sorted().collect(Collectors.toCollection(ArrayList::new));

        System.out.println("RESULTS FROM THE FIRST LOG PROCESSOR");
        firstLogProcessor.processLogs(logsList).forEach(l -> System.out.println(l.toString()));

        System.out.println("RESULTS FROM THE SECOND LOG PROCESSOR");
        secondLogProcessor.processLogs(logsList).forEach(l -> System.out.println(l.toString()));

        System.out.println("RESULTS FROM THE THIRD LOG PROCESSOR");
        thirdLogProcessor.processLogs(logsList).forEach(l -> System.out.println(l.toString()));
        }
}
