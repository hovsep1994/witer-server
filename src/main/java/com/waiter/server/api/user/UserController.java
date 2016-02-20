package com.waiter.server.api.user;

import com.waiter.server.api.common.ResponseEntity;
import com.waiter.server.api.common.ResponseStatus;
import com.waiter.server.api.user.model.request.UserRegistrationModelRequest;
import com.waiter.server.services.company.model.Company;
import com.waiter.server.services.name.model.EntityType;
import com.waiter.server.services.name.model.Name;
import com.waiter.server.services.name.model.TranslationType;
import com.waiter.server.services.user.UserService;
import com.waiter.server.services.user.dto.UserDto;
import com.waiter.server.services.user.model.SignUpStatus;
import com.waiter.server.services.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Admin on 1/17/2016.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<User> login(String email, String password) {
        User findUser = userService.findUserByNamePassword(email, password);
        return new ResponseEntity<>(findUser);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseStatus register(UserRegistrationModelRequest request) {

        UserDto userDto = new UserDto();
        userDto.setPassword(request.getPassword());
        userDto.setEmail(request.getEmail());
//        userDto.setCompanyId();
        userDto.setName(request.getName());
        SignUpStatus signUpStatus = userService.signUp(userDto);
        return new ResponseStatus(signUpStatus.name());
    }

    @RequestMapping(value = "/validate", method = RequestMethod.POST)
    public ResponseStatus validate(UserRegistrationModelRequest request) {

        UserDto userDto = new UserDto();
        userDto.setPassword(request.getPassword());
        userDto.setEmail(request.getEmail());
//        userDto.setCompanyId();
        userDto.setName(request.getName());
        SignUpStatus signUpStatus = userService.validate(userDto);
        return new ResponseStatus(signUpStatus.name());
    }
}
