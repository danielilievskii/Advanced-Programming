package FirstMidtermExercises.Ex10;

public interface IEvaluator<T extends Comparable<T>> {
    public boolean evaluate(T a, T b);
}
