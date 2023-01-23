package web.app.restfulquiz.domain.entity;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class QuizResult{

    private String name;
    private String fullname;
    private String time;
    private List<QuestionDone> questions;
    private Integer score;

}
