package web.app.restfulquiz.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import web.app.restfulquiz.dao.UserDao;
import web.app.restfulquiz.domain.entity.Permission;
import web.app.restfulquiz.domain.entity.User;
import web.app.restfulquiz.domain.entity.UserPerm;
import web.app.restfulquiz.exception.QuizResultNotFoundException;
import web.app.restfulquiz.security.AuthUserDetail;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class UserDetailServiceTest {

    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserDetailService userDetailService;


//    @Test
//    void loadUserByUsernameTest_failed() {
//        List<Permission> permissions = new ArrayList<>();
//        permissions.add(Permission.builder()
//                .permission_id(1)
//                .name("read")
//                .build());
//        permissions.add(Permission.builder()
//                .permission_id(2)
//                .name("write")
//                .build());
//
//        User user = User.builder()
//                .user_id(1)
//                .username("alanz")
//                .password("123456")
//                .firstname("Alan")
//                .lastname("Zhang")
//                .address("Boston")
//                .email("haoyuz0214@163.com")
//                .phone("8573089078")
//                .status(true)
//                .is_admin(false)
//                .build();
//        Optional<UserPerm> userPerm = Optional.ofNullable(new UserPerm(user, permissions));
//
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(new SimpleGrantedAuthority("read"));
//        authorities.add(new SimpleGrantedAuthority("write"));
//
//        UserDetails auth = userDetailService.loadUserByUsername("alanz");
//
//        UserDetails expected = AuthUserDetail.builder()
//                .user_id(1)
//                .username("alanz")
//                .password(auth.getPassword())
//                .firstname("Alan")
//                .lastname("Zhang")
//                .address("Boston")
//                .email("haoyuz0214@163.com")
//                .phone("8573089078")
//                .status(true)
//                .is_admin(false)
//                .authorities(authorities)
//                .accountNonExpired(true)
//                .accountNonLocked(true)
//                .credentialsNonExpired(true)
//                .enabled(true)
//                .build();
//
//        Mockito.when(userDao.loadUserByUsername("alanz")).thenReturn(userPerm);
//        assertEquals(expected, auth);
//        assertThrows(UsernameNotFoundException.class, () -> userDetailService.loadUserByUsername("alanz"));
//    }

}
