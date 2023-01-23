package web.app.restfulquiz.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import web.app.restfulquiz.domain.common.ResponseStatus;
import web.app.restfulquiz.domain.entity.Quiz;
import web.app.restfulquiz.domain.entity.User;
import web.app.restfulquiz.domain.entity.UserWithoutPass;
import web.app.restfulquiz.domain.response.AllQuizzesResponse;
import web.app.restfulquiz.domain.response.AllUsersResponse;
import web.app.restfulquiz.domain.response.UserResponse;
import web.app.restfulquiz.domain.response.UserWithoutPassResponse;
import web.app.restfulquiz.exception.UserNotFoundException;
import web.app.restfulquiz.service.impl.QuizServiceImpl;
import web.app.restfulquiz.service.impl.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class AdminControllerTest {

    @Mock
    private UserServiceImpl userService;

    @Mock
    private QuizServiceImpl quizService;

    @InjectMocks
    private AdminController adminController;


    @Test
    void getAllUsersTest() {
        List<User> users = new ArrayList<>();
        users.add(User.builder()
                .user_id(1)
                .username("alanz")
                .password("123456")
                .firstname("Alan")
                .lastname("Zhang")
                .address("Boston")
                .email("haoyuz0214@gmail.com")
                .phone("8573089078")
                .status(true)
                .is_admin(false)
                .build());

        AllUsersResponse expected = AllUsersResponse.builder()
                .status(ResponseStatus.builder()
                        .success(true)
                        .message("Get all users")
                        .build())
                .users(users)
                .build();

        Mockito.when(userService.getAllUsers()).thenReturn(users);
        assertEquals(expected, adminController.getAllUsers());
    }

    @Test
    void getUserByIdTest_success() throws UserNotFoundException {
        Optional<User> expectedUser = Optional.ofNullable(User.builder()
                .user_id(1)
                .username("alanz")
                .firstname("Alan")
                .lastname("Zhang")
                .address("Boston")
                .email("haoyuz0214@gmail.com")
                .phone("8573089078")
                .status(true)
                .is_admin(false)
                .build());

        UserWithoutPassResponse expected = UserWithoutPassResponse.builder()
                .status(ResponseStatus.builder()
                        .success(true)
                        .message("Get the user")
                        .build())
                .user(new UserWithoutPass(expectedUser.get()))
                .build();

        Mockito.when(userService.getUserById(1)).thenReturn(expectedUser);

        assertEquals(expected, adminController.getUserById(1));

    }

    @Test
    void getUserByIdTest_failed() {
        Mockito.when(userService.getUserById(-1)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> adminController.getUserById(-1));
    }


    @Test
    void updateUserStatus_success() throws UserNotFoundException {
        Optional<User> expectedUser = Optional.ofNullable(User.builder()
                .user_id(1)
                .username("alanz")
                .firstname("Alan")
                .lastname("Zhang")
                .address("Boston")
                .email("haoyuz0214@gmail.com")
                .phone("8573089078")
                .status(false)
                .is_admin(false)
                .build());

        UserResponse expected = UserResponse.builder()
                .status(ResponseStatus.builder()
                        .success(true)
                        .message("User status updated")
                        .build())
                .user(expectedUser.get())
                .build();

        Mockito.when(userService.updateUserStatus(1)).thenReturn(expectedUser);

        assertEquals(expected, adminController.updateUserStatus(1));
    }


    @Test
    void updateUserStatus_failed() {
        Mockito.when(userService.updateUserStatus(-1)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> adminController.updateUserStatus(-1));
    }


    @Test
    void getQuizzesByCategoryTest() {
        List<Quiz> quizzes = new ArrayList<>();
        quizzes.add(Quiz.builder()
                .quiz_id(1)
                .category_id(1)
                .name("Java")
                .build());

        AllQuizzesResponse expected = AllQuizzesResponse.builder()
                .status(ResponseStatus.builder()
                        .success(true)
                        .message("Get all quizzes under the category")
                        .build())
                .quizzes(quizzes)
                .build();

        Mockito.when(quizService.getQuizzesByCategory(1)).thenReturn(quizzes);
        assertEquals(expected, adminController.getQuizzesByCategory(1));
    }




}
