package com.waiter.server.api;

import com.waiter.server.api.common.ResponseEntity;
import com.waiter.server.services.company.model.Company;
import com.waiter.server.services.user.UserService;
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
    public ResponseEntity<User> register(String name, String email, String password,
                                         String phone, String companyName) {
        Company company = new Company();
        company.setName(companyName);
        company.setPhone(phone);

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setCompany(company);

        User findUser = userService.create(user);
        return new ResponseEntity<>(findUser);
    }
}
