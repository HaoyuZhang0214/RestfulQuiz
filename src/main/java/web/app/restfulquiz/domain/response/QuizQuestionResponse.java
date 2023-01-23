package web.app.restfulquiz.domain.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import web.app.restfulquiz.domain.common.ResponseStatus;
import web.app.restfulquiz.domain.entity.QuizQues;

import java.util.List;

@Getter
@Setter
@Builder
public class QuizQuestionResponse {

    private ResponseStatus status;

    private QuizQues quiz;

}
