package com.waiter.server.services.user;

import com.waiter.server.commons.APIException;
import com.waiter.server.commons.entities.User;

/**
 * Created by Admin on 12/15/2015.
 */
public interface UserService {
    User create(User user);

    User login(String login, String password) throws APIException;

    User authenticate(String key) throws APIException;

    boolean validateEmail(String hash);
}
