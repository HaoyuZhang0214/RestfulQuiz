package web.app.restfulquiz.domain.entity;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
// user class with permission list
public class UserPerm {

    private Integer user_id;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String address;
    private String email;
    private String phone;
    private boolean status;
    private boolean is_admin;
    private List<Permission> permissions;

    public UserPerm(User user, List<Permission> permissions) {
        this.user_id = user.getUser_id();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.address = user.getAddress();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.status = user.isStatus();
        this.is_admin = user.is_admin();
        this.permissions = permissions;
    }

}
