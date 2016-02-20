package com.waiter.server.services.user;

import com.waiter.server.services.user.dto.UserDto;
import com.waiter.server.services.user.model.SignUpStatus;
import com.waiter.server.services.user.model.User;

/**
 * Created by Admin on 12/15/2015.
 */
public interface UserService {

    SignUpStatus signUp(UserDto userDto);

    SignUpStatus validate(UserDto userDto);

    User findUserByNamePassword(String name, String password);

    User authenticate(String key);

    boolean validateEmail(String hash);
}
