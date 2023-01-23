package web.app.restfulquiz.domain.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import web.app.restfulquiz.domain.common.ResponseStatus;
import web.app.restfulquiz.domain.entity.Question;

@Getter
@Setter
@Builder
public class QuestionResponse {

    private ResponseStatus status;
    private Question question;

}
