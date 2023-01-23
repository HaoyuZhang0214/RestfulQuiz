package web.app.restfulquiz.domain.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
public class QuizQues {

    private int quiz_id;
    private int category_id;
    private String name;
    private List<QuestionOps> questions;

    public QuizQues(Quiz quiz, List<QuestionOps> questions) {
        this.quiz_id = quiz.getQuiz_id();
        this.category_id = quiz.getCategory_id();
        this.name = quiz.getName();
        this.questions = questions;
    }

}
