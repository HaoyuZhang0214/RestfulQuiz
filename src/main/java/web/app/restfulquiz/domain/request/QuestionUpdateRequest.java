package web.app.restfulquiz.domain.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import web.app.restfulquiz.domain.entity.Option;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Builder
public class QuestionUpdateRequest {

    @NotNull(message = "Content is required")
    private Integer question_id;

    @NotNull(message = "Content is required")
    private String content;

    @NotNull(message = "Options are required")
    private List<Option> options;

}
