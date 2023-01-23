package web.app.restfulquiz.domain.response;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import web.app.restfulquiz.domain.common.ResponseStatus;
import web.app.restfulquiz.domain.entity.User;

@Getter
@Setter
@Builder
@EqualsAndHashCode
public class UserResponse {

    private ResponseStatus status;
    private User user;
}
