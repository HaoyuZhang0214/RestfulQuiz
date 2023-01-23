package web.app.restfulquiz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import web.app.restfulquiz.dao.UserDao;
import web.app.restfulquiz.domain.entity.User;
import web.app.restfulquiz.domain.request.RegisterRequest;
import web.app.restfulquiz.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class UserServiceImpl implements UserService {

    private UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public Optional<User> createUser(RegisterRequest request) {
        return userDao.createUser(request);
    }

    @Override
    @Cacheable("users")
    public Optional<User> getUserById(int user_id) {
        return userDao.getUserById(user_id);
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsersExceptAdmin();
    }

    @Override
    @CachePut(cacheNames = "users", key = "#user_id")
    public Optional<User> updateUserStatus(int user_id) {
        return userDao.updateUserStatus(user_id);
    }

    // ------Async-------
    @Async("taskExecutor")
    public CompletableFuture<Optional<User>> getUserByIdAsync(int user_id) {
        Optional<User> user = userDao.getUserById(user_id);
        return CompletableFuture.completedFuture(user);
    }

}
