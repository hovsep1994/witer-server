package com.waiter.server.services.user.impl;

import com.waiter.server.persistence.core.repository.user.UserRepository;
import com.waiter.server.services.common.exception.ServiceRuntimeException;
import com.waiter.server.services.user.UserService;
import com.waiter.server.services.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

/**
 * Created by Admin on 12/15/2015.
 */
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User create(User user) {
        return userRepository.save(user);
    }

    @Override
    public User login(String login, String password) {
        Assert.notNull(login, "login must nor be null");
        Assert.notNull(password, "password must nor be null");
        User user = userRepository.findByUsernameAndPassword(login, password);
        if(user == null){
            throw new ServiceRuntimeException("No user found with login " + login + " password " + password);
        }
        return user;
    }

    @Override
    public User authenticate(String token) {
        Assert.notNull(token, "token must nor be null");
        User user = userRepository.findUserByToken(token);
        if(user == null){
            throw new ServiceRuntimeException("No user found with token " + token);
        }
        return user;
    }

    @Override
    public boolean validateEmail(String hash) {
        User user = userRepository.findUserByHash(hash);
        return user != null;
    }
}
