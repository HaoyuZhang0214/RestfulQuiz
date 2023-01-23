package web.app.restfulquiz.domain.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import web.app.restfulquiz.domain.common.ResponseStatus;

@Getter
@Setter
@Builder
public class LoginResponse {
    private ResponseStatus status;
    private String token;

}
