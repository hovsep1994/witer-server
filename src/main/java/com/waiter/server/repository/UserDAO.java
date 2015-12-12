package com.waiter.server.repository;

import com.waiter.server.commons.APIException;
import com.waiter.server.commons.entities.User;

/**
 * @author shahenpoghosyan
 */
public interface UserDAO {

    long create(User user);
    User login(String login, String password) throws APIException;
    User authenticate(String key) throws APIException;
    boolean validateEmail(String hash);
}
