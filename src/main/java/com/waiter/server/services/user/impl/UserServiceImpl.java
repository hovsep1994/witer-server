package com.waiter.server.services.user.impl;

import com.waiter.server.commons.APIException;
import com.waiter.server.commons.entities.User;
import com.waiter.server.repository.sql.UserRepository;
import com.waiter.server.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Admin on 12/15/2015.
 */
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User create(User user) {
        return userRepository.create(user);
    }

    @Override
    public User login(String login, String password) throws APIException {
        return userRepository.login(login, password);
    }

    @Override
    public User authenticate(String key) throws APIException {
        return userRepository.authenticate(key);
    }

    @Override
    public boolean validateEmail(String hash) {
        return userRepository.validateEmail(hash);
    }
}
