package web.app.restfulquiz.domain.response;

import lombok.*;
import web.app.restfulquiz.domain.common.ResponseStatus;
import web.app.restfulquiz.domain.entity.Question;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class AllQuestionsResponse {

    private ResponseStatus status;
    private List<Question> questions;
}
