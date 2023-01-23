package web.app.restfulquiz.domain.entity;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class UserWithoutPass {

    private int user_id;

    private String username;

    private String firstname;

    private String lastname;

    private String address;

    private String email;

    private String phone;

    private boolean status;

    private boolean is_admin;

    public UserWithoutPass(User user) {
        this.user_id = user.getUser_id();
        this.username = user.getUsername();
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.address = user.getAddress();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.status = user.isStatus();
        this.is_admin = user.is_admin();
    }

}
