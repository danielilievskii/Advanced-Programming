package LabaratoryExercises.Lab5.Timestamp;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Timestamp<T> implements Comparable<Timestamp<?>> {
    private final LocalDateTime time;
    private final T element;
    public Timestamp(LocalDateTime time, T element){
        this.time=time;
        this.element=element;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public T getElement() {
        return element;
    }

    @Override
    public int compareTo(Timestamp<?> o) {
        return time.compareTo(o.time);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Timestamp<?> timestamp = (Timestamp<?>) o;
        return Objects.equals(time, timestamp.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(time, element);
    }

    @Override
    public String toString() {
        return time.toString() + " " + element;
    }
}

class Scheduler<T> {
    private List<Timestamp<T>> timestamps;

    public Scheduler() {
        this.timestamps = new ArrayList<>();
    }

    public void add(Timestamp<T> t) {
        timestamps.add(t);
    }

    public boolean remove(Timestamp<T> t) {
        return timestamps.remove(t);
    }

    public Timestamp<T> next() {
        return timestamps.stream().filter(x -> x.getTime().isAfter(LocalDateTime.now())).min(Timestamp::compareTo).orElse(null);
    }

    public Timestamp<T> last() {
        return timestamps.stream().filter(x -> x.getTime().isBefore(LocalDateTime.now())).max(Timestamp::compareTo).orElse(null);
    }

    public List<Timestamp<T>> getAll(LocalDateTime begin, LocalDateTime end) {
        return timestamps.stream().filter(x -> x.getTime().isAfter(begin) && x.getTime().isBefore(end)).collect(Collectors.toList());
    }

}
