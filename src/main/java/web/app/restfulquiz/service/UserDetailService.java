package web.app.restfulquiz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import web.app.restfulquiz.dao.UserDao;
import web.app.restfulquiz.domain.entity.Permission;
import web.app.restfulquiz.domain.entity.UserPerm;
import web.app.restfulquiz.security.AuthUserDetail;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserDetailService implements UserDetailsService {

    private UserDao userDao;

    @Autowired
    public UserDetailService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserPerm> userOptional = userDao.loadUserByUsername(username);

        if (!userOptional.isPresent()){
            throw new UsernameNotFoundException("Username does not exist");
        }

        UserPerm user = userOptional.get();

        return AuthUserDetail.builder() // spring security's userDetail
                .user_id(user.getUser_id())
                .username(user.getUsername())
                .password(new BCryptPasswordEncoder().encode(user.getPassword()))
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .address(user.getAddress())
                .email(user.getEmail())
                .phone(user.getPhone())
                .status(user.isStatus())
                .is_admin(user.is_admin())
                .authorities(getAuthoritiesFromUser(user))
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .build();
    }

    private List<GrantedAuthority> getAuthoritiesFromUser(UserPerm user){
        List<GrantedAuthority> userAuthorities = new ArrayList<>();

        for (Permission permission :  user.getPermissions()){
            userAuthorities.add(new SimpleGrantedAuthority(permission.getName()));
        }

        return userAuthorities;
    }
}
