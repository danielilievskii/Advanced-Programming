package SecondMidtermExercises.Ex11;

import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

class DeadlineNotValidException extends Exception {
    public DeadlineNotValidException(LocalDateTime deadline) {
        super(String.format("The deadline %s has already passed", deadline));
    }
}

interface ITask {
    String getCategory();
    int getPriority();
    LocalDateTime getDeadline();
}
class SimpleTask implements ITask {
    String category;
    String name;
    String description;

    public SimpleTask(String category, String name, String description) {
        this.category = category;
        this.name = name;
        this.description = description;
    }

    @Override
    public String getCategory() {
        return category;
    }

    @Override
    public int getPriority() {
        return Integer.MAX_VALUE;
    }

    @Override
    public LocalDateTime getDeadline() {
        return LocalDateTime.MAX;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Task{");
        sb.append("name='").append(name).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

abstract class TaskDecorator implements ITask {
    ITask iTask;
    public TaskDecorator(ITask iTask) {
        this.iTask = iTask;
    }
}

class PriorityTaskDecorator extends TaskDecorator {
    int priority;
    public PriorityTaskDecorator(ITask iTask, int priority) {
        super(iTask);
        this.priority = priority;
    }

    @Override
    public String getCategory() {
        return iTask.getCategory();
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public LocalDateTime getDeadline() {
        return iTask.getDeadline();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(iTask.toString(), 0, iTask.toString().length()-1);
        sb.append(", priority=").append(priority);
        sb.append('}');
        return sb.toString();
    }
}

class TimeTaskDecorator extends TaskDecorator {
    LocalDateTime deadLine;
    public TimeTaskDecorator(ITask iTask, LocalDateTime deadLine) {
        super(iTask);
        this.deadLine = deadLine;
    }

    @Override
    public String getCategory() {
        return iTask.getCategory();
    }

    @Override
    public int getPriority() {
        return iTask.getPriority();
    }

    @Override
    public LocalDateTime getDeadline() {
        return deadLine;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(iTask.toString(), 0, iTask.toString().length()-1);
        sb.append(", deadline=").append(deadLine);
        sb.append('}');
        return sb.toString();
    }
}

class TaskFactory {
    public static ITask createTask(String line) throws DeadlineNotValidException {
        String [] parts = line.split(",");
        String category = parts[0];
        String name = parts[1];
        String description = parts[2];
        SimpleTask simpleTask = new SimpleTask(category, name, description);
        if(parts.length == 3) {
            return simpleTask;
        } else if(parts.length == 4) {
            try {
                int priority = Integer.parseInt(parts[3]);
                return new PriorityTaskDecorator(simpleTask, priority);
            } catch (Exception e) {
                LocalDateTime deadline = LocalDateTime.parse(parts[3]);
                checkDeadline(deadline);
                return new TimeTaskDecorator(simpleTask, deadline);
            }
        } else  {
            LocalDateTime deadline = LocalDateTime.parse(parts[3]);
            checkDeadline(deadline);
            int priority = Integer.parseInt(parts[4]);
            return new PriorityTaskDecorator(new TimeTaskDecorator(simpleTask, deadline), priority);
        }
    }
    private static void checkDeadline(LocalDateTime deadline) throws DeadlineNotValidException {
        LocalDateTime dateTime = LocalDateTime.of(2020, 6, 2, 0, 0, 0);
        if (deadline.isBefore(dateTime)) {
            throw new DeadlineNotValidException(deadline);
        }
    }
}
class TaskManager {

    Map<String, List<ITask>> tasksMap;

    public TaskManager() {
        tasksMap = new TreeMap<>();
    }

    void readTasks(InputStream inputStream) {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

        tasksMap = br.lines()
                .map(line -> {
                    try {
                        return TaskFactory.createTask(line);
                    } catch (DeadlineNotValidException e) {
                        System.out.println(e.getMessage());
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(
                        ITask::getCategory,
                        TreeMap::new,
                        Collectors.toCollection(ArrayList::new)
                ));

    }

    void printTasks(OutputStream os, boolean includePriority, boolean includeCategory) {
        PrintWriter pw = new PrintWriter(os);

        Comparator<ITask> priorityComparator = Comparator.comparing(ITask::getPriority).thenComparing(task -> Duration.between(LocalDateTime.now(), task.getDeadline()));
        Comparator<ITask> simpleComparator = Comparator.comparing(task -> Duration.between(LocalDateTime.now(), task.getDeadline()));

        if(includeCategory) {
            tasksMap.forEach((category, tasks) -> {
                pw.println(category.toUpperCase());
                tasks.stream().sorted(includePriority ? priorityComparator : simpleComparator).forEach(pw::println);
            });
        } else {
            tasksMap.values().stream()
                    .flatMap(Collection::stream)
                    .sorted(includePriority ? priorityComparator : simpleComparator)
                    .forEach(pw::println);
        }
        pw.flush();
    }
}

public class TasksManagerTest {

    public static void main(String[] args) {

        TaskManager manager = new TaskManager();

        System.out.println("Tasks reading");
        manager.readTasks(System.in);
        System.out.println("By categories with priority");
        manager.printTasks(System.out, true, true);
        System.out.println("-------------------------");
        System.out.println("By categories without priority");
        manager.printTasks(System.out, false, true);
        System.out.println("-------------------------");
        System.out.println("All tasks without priority");
        manager.printTasks(System.out, false, false);
        System.out.println("-------------------------");
        System.out.println("All tasks with priority");
        manager.printTasks(System.out, true, false);
        System.out.println("-------------------------");

    }
}

