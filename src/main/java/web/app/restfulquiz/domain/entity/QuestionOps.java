package web.app.restfulquiz.domain.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
public class QuestionOps {

    private Integer question_id;
    private Integer quiz_id;
    private String content;
    private boolean status;
    private List<Option> options;

    public QuestionOps(Question question, List<Option> options) {
        this.question_id = question.getQuestion_id();
        this.quiz_id = question.getQuiz_id();
        this.content = question.getContent();
        this.status = question.isStatus();
        this.options = options;
    }
}
