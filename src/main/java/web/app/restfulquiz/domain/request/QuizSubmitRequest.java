package web.app.restfulquiz.domain.request;

import lombok.Getter;
import lombok.Setter;
import web.app.restfulquiz.domain.entity.QuestionSubmit;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class QuizSubmitRequest {

    @NotNull(message = "Quiz_id is required")
    private Integer quiz_id;

    @NotNull(message = "User_id is required")
    private Integer user_id;

    @NotNull(message = "QuestionSubmits are required")
    private List<QuestionSubmit> questionSubmits;

}
