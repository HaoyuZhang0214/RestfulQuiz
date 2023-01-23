package web.app.restfulquiz.AOP;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import web.app.restfulquiz.domain.response.ErrorResponse;
import web.app.restfulquiz.exception.CredentialInvalidException;
import web.app.restfulquiz.exception.QuizResultNotFoundException;
import web.app.restfulquiz.exception.UserNotFoundException;

@ControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity handleException(Exception e){
        return new ResponseEntity(ErrorResponse.builder().message("Different Message").build(), HttpStatus.OK);
    }

    @ExceptionHandler(value = {UserNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException e){
        return new ResponseEntity(ErrorResponse.builder().message(e.getMessage()).build(), HttpStatus.OK);
    }

    @ExceptionHandler(value = {QuizResultNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleQuizResultNotFoundException(QuizResultNotFoundException e){
        return new ResponseEntity(ErrorResponse.builder().message(e.getMessage()).build(), HttpStatus.OK);
    }

    @ExceptionHandler(value = {CredentialInvalidException.class})
    public ResponseEntity<ErrorResponse> handleCredentialInvalidException(CredentialInvalidException e){
        return new ResponseEntity(ErrorResponse.builder().message(e.getMessage()).build(), HttpStatus.OK);
    }


}
