package FirstMidtermExercises.Ex23;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

public class Quiz {
    List<Question> questions;

    public Quiz() {
        this.questions = new ArrayList<>();
    }

    void addQuestion(String questionData) throws InvalidOperationException {
        try {
            questions.add(Question.createQuestion(questionData));
        } catch (InvalidOperationException e) {
            System.out.println(e.getMessage());
        }
    }

    void printQuiz(OutputStream os) {
        PrintWriter pw = new PrintWriter(os);
        questions.stream().sorted(Comparator.reverseOrder()).forEach(q -> pw.println(q));
        pw.flush();
    }

    void answerQuiz (List<String> answers, OutputStream os) throws InvalidOperationException{
        if (answers.size()!=questions.size()){
            throw new InvalidOperationException("Answers and questions must be of same length!");
        }
        PrintWriter pw = new PrintWriter(os);
        double totalPoints = IntStream.range(0, answers.size()).mapToDouble(i -> {
            double gainedPoints = questions.get(i).answer(answers.get(i));
            pw.println(String.format("%d. %.2f", i+1, gainedPoints));
            return gainedPoints;
        }).sum();

        pw.println(String.format("Total points: %.2f", totalPoints));
        pw.flush();
    }

}
