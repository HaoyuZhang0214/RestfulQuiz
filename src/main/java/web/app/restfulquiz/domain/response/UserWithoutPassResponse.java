package web.app.restfulquiz.domain.response;

import lombok.*;
import web.app.restfulquiz.domain.common.ResponseStatus;
import web.app.restfulquiz.domain.entity.UserWithoutPass;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class UserWithoutPassResponse {

    private ResponseStatus status;
    private UserWithoutPass user;

}
