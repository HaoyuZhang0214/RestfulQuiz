package web.app.restfulquiz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import web.app.restfulquiz.domain.common.ResponseStatus;
import web.app.restfulquiz.domain.entity.User;
import web.app.restfulquiz.domain.request.LoginRequest;
import web.app.restfulquiz.domain.request.RegisterRequest;
import web.app.restfulquiz.domain.response.LoginResponse;
import web.app.restfulquiz.domain.response.RegisterResponse;
import web.app.restfulquiz.exception.CredentialInvalidException;
import web.app.restfulquiz.security.AuthUserDetail;
import web.app.restfulquiz.security.JwtProvider;
import web.app.restfulquiz.service.UserService;
import web.app.restfulquiz.service.impl.UserServiceImpl;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class LoginController {

    private UserService userService;
    private AuthenticationManager authenticationManager;
    private JwtProvider jwtProvider;

    @Autowired
    public LoginController(UserServiceImpl userServiceImpl, AuthenticationManager authenticationManager, JwtProvider jwtProvider) {
        this.userService = userServiceImpl;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }


    @GetMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request,
                               BindingResult bindingResult) throws CredentialInvalidException {
        if(bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            errors.forEach(error -> System.out.println(
                    "ValidationError in " + error.getObjectName() + ": " + error.getDefaultMessage()));
            return LoginResponse.builder()
                    .status(ResponseStatus.builder()
                            .success(false)
                            .message("Validation error")
                            .build())
                    .build();
        }

        Authentication authentication;
        try{
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
        } catch (AuthenticationException e){
            throw new CredentialInvalidException("Provided credential is invalid.");
//            return LoginResponse.builder()
//                    .status(ResponseStatus.builder()
//                            .success(false)
//                            .message("Provided credential is invalid.")
//                            .build())
//                    .build();
        }

        AuthUserDetail authUserDetail = (AuthUserDetail) authentication.getPrincipal(); //getPrincipal() returns the user object

        //A token wil be created using the username/email/userId and permission
        String token = jwtProvider.createToken(authUserDetail);

        //Returns the token as a response to the frontend/postman
        return LoginResponse.builder()
                .status(ResponseStatus.builder()
                        .success(true)
                        .message("Welcome " + authUserDetail.getUsername())
                        .build())
                .token(token)
                .build();

    }


    @PostMapping("/register")
    public RegisterResponse signUp(@Valid @RequestBody RegisterRequest request,
                                   BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            errors.forEach(error -> System.out.println(
                    "ValidationError in " + error.getObjectName() + ": " + error.getDefaultMessage()));
            return RegisterResponse.builder()
                    .status(ResponseStatus.builder()
                            .success(false)
                            .message("Validation error")
                            .build())
                    .build();
        }

        Optional<User> user = userService.createUser(request);
        if(!user.isPresent()) {
            return RegisterResponse.builder()
                    .status(ResponseStatus.builder()
                            .success(false)
                            .message("Same username exists")
                            .build())
                    .build();
        }

        return RegisterResponse.builder()
                .user(user.get())
                .status(ResponseStatus.builder()
                        .success(true)
                        .message("Register successfully")
                        .build())
                .build();

    }



}
