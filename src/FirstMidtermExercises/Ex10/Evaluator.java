package FirstMidtermExercises.Ex10;

public class Evaluator {
    public static <T extends Comparable<T>> boolean evaluateExpression (T left, T right, String operator) {
        IEvaluator<T> evaluator = EvaluatorBuilder.build(operator);
        return evaluator.evaluate(left, right);
    }
}
