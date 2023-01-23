package web.app.restfulquiz.domain.request;

import lombok.Getter;
import lombok.Setter;
import web.app.restfulquiz.domain.entity.Option;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class QuestionRequest {

    @NotNull(message = "Quiz_id is required")
    private Integer quiz_id;

    @NotNull(message = "Content is required")
    private String content;

    @NotNull(message = "Options are required")
    private List<Option> options;


}
