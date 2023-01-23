package web.app.restfulquiz.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import web.app.restfulquiz.dao.UserDao;
import web.app.restfulquiz.domain.entity.User;
import web.app.restfulquiz.service.impl.UserServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserServiceImpl userService;


    @Test
    void getUserByIdTest() {
        Optional<User> expected = Optional.ofNullable(User.builder()
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

        Mockito.when(userDao.getUserById(1)).thenReturn(expected);
        assertEquals(expected, userService.getUserById(1));
    }


    @Test
    void getUserByUsernameTest() {
        Optional<User> expected = Optional.ofNullable(User.builder()
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

        Mockito.when(userDao.getUserByUsername("alanz")).thenReturn(expected);
        assertEquals(expected, userService.getUserByUsername("alanz"));
    }


    @Test
    void updateUserStatusTest() {
        Optional<User> expected = Optional.ofNullable(User.builder()
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

        Mockito.when(userDao.updateUserStatus(1)).thenReturn(expected);
        assertEquals(expected, userService.updateUserStatus(1));
    }





}
