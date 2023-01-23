package web.app.restfulquiz.domain.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class QuestionSubmit {

    private Integer question_id;
    private Integer option_id;

}
