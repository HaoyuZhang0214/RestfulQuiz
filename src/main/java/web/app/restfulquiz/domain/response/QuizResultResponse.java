package web.app.restfulquiz.domain.response;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import web.app.restfulquiz.domain.common.ResponseStatus;
import web.app.restfulquiz.domain.entity.QuizResult;

@Builder
@EqualsAndHashCode
public class QuizResultResponse {

    private ResponseStatus status;
    private QuizResult quizResult;

}
