package web.app.restfulquiz.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.PropertyValues;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import web.app.restfulquiz.domain.common.ResponseStatus;
import web.app.restfulquiz.domain.entity.User;
import web.app.restfulquiz.domain.request.RegisterRequest;
import web.app.restfulquiz.domain.response.RegisterResponse;
import web.app.restfulquiz.service.impl.UserServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class LoginControllerTest {

    @Mock
    private UserServiceImpl userService;

    @InjectMocks
    private LoginController loginController;


    @Test
    void signUpTest_success() {
        RegisterRequest request = RegisterRequest.builder()
                .username("user5")
                .password("pass5")
                .firstname("firstname5")
                .lastname("lastname5")
                .build();

        Optional<User> user = Optional.ofNullable(User.builder()
                .user_id(5)
                .username("user5")
                .password("pass5")
                .firstname("firstname5")
                .lastname("lastname5")
                .status(true)
                .is_admin(false)
                .build());

        RegisterResponse expected = RegisterResponse.builder()
                .user(user.get())
                .status(ResponseStatus.builder()
                        .success(true)
                        .message("Register successfully")
                        .build())
                .build();

        Mockito.when(userService.createUser(request)).thenReturn(user);
        assertEquals(expected, loginController.signUp(request, new BeanPropertyBindingResult(request, "request")));

    }


    @Test
    void signUpTest_failed() {
        RegisterRequest request = RegisterRequest.builder()
                .username("user5")
                .password("pass5")
                .firstname("firstname5")
                .lastname("lastname5")
                .build();

        RegisterResponse expected = RegisterResponse.builder()
                .status(ResponseStatus.builder()
                        .success(false)
                        .message("Same username exists")
                        .build())
                .build();

        Mockito.when(userService.createUser(request)).thenReturn(Optional.empty());
        assertEquals(expected, loginController.signUp(request, new BeanPropertyBindingResult(request, "request")));

    }

}
