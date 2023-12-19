package FirstMidtermExercises.Ex23;

public class TrueFalseQuestion extends Question{
    boolean answer;

    public TrueFalseQuestion(String text, int point, boolean answer) {
        super(text, point);
        this.answer = answer;
    }

    @Override
    public String toString() {
       return String.format("True/False Question: %s Points: %d Answer: %s", text, points, answer);
    }


    @Override
    double answer(String studentAnswer) {
        if(Boolean.parseBoolean(studentAnswer) == answer) {
            return points;
        } else return 0.0;
    }
}
