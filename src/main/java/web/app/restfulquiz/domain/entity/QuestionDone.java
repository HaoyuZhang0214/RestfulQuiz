package web.app.restfulquiz.domain.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class QuestionDone {

    private Integer question_id;
    private Integer quiz_id;
    private String content;
    private boolean status;
    private List<Option> options;
    private Integer choice;

    public QuestionDone(QuestionOps questionOps, Integer choice) {
        this.question_id = questionOps.getQuestion_id();
        this.quiz_id = questionOps.getQuiz_id();
        this.content = questionOps.getContent();
        this.status = questionOps.isStatus();
        this.options = questionOps.getOptions();
        this.choice = choice;
    }

}
