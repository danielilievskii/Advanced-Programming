package FirstMidtermExercises.Ex23;

public class MultipleChoiceQuestion extends Question{
    String answer;

    public MultipleChoiceQuestion(String text, int point, String answer) throws InvalidOperationException{
        super(text, point);
        if(answer.charAt(0)<'A' || answer.charAt(0)>'E')
            throw new InvalidOperationException(String.format("%s is not allowed option for this question", answer));
        this.answer = answer;
    }

    @Override
    public String toString() {
        return String.format("Multiple Choice Question: %s Points %d Answer: %s", text, points, answer);
    }

    @Override
    double answer(String studentAnswer) {
        if(answer.equals(studentAnswer)) {
            return points;
        } else return points * -0.2;
    }
}
