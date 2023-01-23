package web.app.restfulquiz.domain.response;

import lombok.*;
import web.app.restfulquiz.domain.common.ResponseStatus;
import web.app.restfulquiz.domain.entity.User;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class RegisterResponse {

    private ResponseStatus status;

    private User user;

}
