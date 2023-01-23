package web.app.restfulquiz.service;

import web.app.restfulquiz.domain.entity.User;
import web.app.restfulquiz.domain.request.RegisterRequest;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface UserService {

    Optional<User> createUser(RegisterRequest request);

    Optional<User> getUserById(int user_id);

    Optional<User> getUserByUsername(String username);

    List<User> getAllUsers();

    Optional<User> updateUserStatus(int user_id);

    //-----Async-----
    CompletableFuture<Optional<User>> getUserByIdAsync(int user_id);

}
