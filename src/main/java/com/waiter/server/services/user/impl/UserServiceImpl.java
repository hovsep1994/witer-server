package com.waiter.server.services.user.impl;

import com.waiter.server.persistence.core.repository.user.UserRepository;
import com.waiter.server.services.common.exception.Assert;
import com.waiter.server.services.common.exception.ErrorCode;
import com.waiter.server.services.common.exception.ServiceException;
import com.waiter.server.services.common.exception.ServiceRuntimeException;
import com.waiter.server.services.user.UserService;
import com.waiter.server.services.user.dto.UserDto;
import com.waiter.server.services.user.model.SignUpStatus;
import com.waiter.server.services.user.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by Admin on 12/15/2015.
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public User signUp(UserDto userDto) throws ServiceException {
        Assert.isTrue(userDto.getPassword().length() > 5, "password must not be less than 6 characters");
        if (checkExistenceByEmail(userDto.getEmail())) {
            LOGGER.debug("User with email -{} exists", userDto.getEmail());
            throw new ServiceException(ErrorCode.DUPLICATE_EMAIL,
                    "User with email " + userDto.getEmail() + "exists");
        }
        User user = new User();
        userDto.updateProperties(user);
        user.setToken(UUID.randomUUID().toString());
        return userRepository.save(user);
    }

    @Override
    public User signIn(String email, String password) {
        Assert.notNull(email, "email must not be null");
        Assert.notNull(password, "password must not be null");
        User user = userRepository.findByEmailAndPassword(email, password);
        if (user == null) {
            LOGGER.error("User not found with password -{} and email -{}", password, email);
            throw new ServiceRuntimeException(ErrorCode.NOT_FOUND, "User not found");
        }
        if (user.getToken() == null) {
            user.setToken(UUID.randomUUID().toString());
            user = userRepository.save(user);
        }
        return user;
    }

    @Override
    public SignUpStatus validate(String email) {
        if (checkExistenceByEmail(email)) {
            return SignUpStatus.EMAIL_EXISTS;
        }
        return SignUpStatus.OK;
    }

    @Override
    public User findUserByNamePassword(String name, String password) {
        Assert.notNull(name, "name must not be null");
        Assert.notNull(password, "password must not be null");
        User user = userRepository.findByNameAndPassword(name, password);
        if (user == null) {
            LOGGER.error("User not found with password -{} and name -{}", password, name);
            throw new ServiceRuntimeException(ErrorCode.NOT_FOUND, "User not found");
        }
        return user;
    }

    @Override
    public User authenticate(String token) {
        Assert.notNull(token, "token must not be null");
        User user = userRepository.findUserByToken(token);
        if (user == null) {
            LOGGER.error("User not found with token -{}", token);
            throw new ServiceRuntimeException(ErrorCode.NOT_FOUND, "User not found");
        }
        return user;
    }

    @Override
    public boolean validateEmail(String hash) {
        User user = userRepository.findUserByHash(hash);
        return user != null;
    }

    public boolean checkExistenceByEmail(String email) {
        return userRepository.countByEmail(email) > 0;
    }
}
