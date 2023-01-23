package web.app.restfulquiz.domain.response;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import web.app.restfulquiz.domain.common.ResponseStatus;
import web.app.restfulquiz.domain.entity.Quiz;

import java.util.List;


@Getter
@Setter
@Builder
@EqualsAndHashCode
public class AllQuizzesResponse {

    private ResponseStatus status;
    private List<Quiz> quizzes;

}
