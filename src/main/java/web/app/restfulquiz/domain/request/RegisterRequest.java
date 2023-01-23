package web.app.restfulquiz.domain.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
public class RegisterRequest {

    @NotNull(message = "Username is required")
    private String username;

    @NotNull(message = "Password is required")
    private String password;

    @NotNull(message = "First name is required")
    private String firstname;

    @NotNull(message = "Last name is required")
    private String lastname;

}
