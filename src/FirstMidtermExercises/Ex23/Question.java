package FirstMidtermExercises.Ex23;

import java.util.Comparator;

public abstract class Question implements Comparable<Question> {
    String text;
    int points;

    public Question(String text, int point) {
        this.text = text;
        this.points = point;
    }

    static Question createQuestion(String questionData) throws InvalidOperationException {
        String[] parts = questionData.split(";");
        String type = parts[0];
        int points = Integer.parseInt(parts[2]);
        String questionText = parts[1];

        if(type.equals("TF")) {
            boolean trueOrFalse = Boolean.parseBoolean(parts[3]);
            return new TrueFalseQuestion(questionText, points, trueOrFalse);
        } else  {
            String correctChoice = parts[3];
            return new MultipleChoiceQuestion(questionText, points, correctChoice);
        }
    }

    @Override
    public int compareTo(Question o) {
        return Integer.compare(this.points, o.points);
    }

    abstract double answer(String studentAnswer);
}
