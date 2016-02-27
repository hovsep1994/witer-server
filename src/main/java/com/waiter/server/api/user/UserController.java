package com.waiter.server.api.user;

import com.waiter.server.api.common.ResponseEntity;
import com.waiter.server.api.common.ResponseStatus;
import com.waiter.server.api.user.model.UserModel;
import com.waiter.server.api.user.model.request.UserRegistrationModelRequest;
import com.waiter.server.api.user.model.request.UserValidationModelRequest;
import com.waiter.server.services.common.exception.ServiceException;
import com.waiter.server.services.company.CompanyService;
import com.waiter.server.services.company.dto.CompanyDto;
import com.waiter.server.services.company.model.Company;
import com.waiter.server.services.user.UserService;
import com.waiter.server.services.user.dto.UserDto;
import com.waiter.server.services.user.model.SignUpStatus;
import com.waiter.server.services.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
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

    @Autowired
    private CompanyService companyService;

    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    public ResponseEntity<UserModel> login(String email, String password) {
        User user = userService.findUserByNamePassword(email, password);
        UserModel userModel = new UserModel();
        userModel.setEmail(user.getEmail());
        userModel.setName(user.getName());
        userModel.setToken(user.getToken());
        return ResponseEntity.forResponse(userModel);
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ResponseStatus register(@RequestBody UserRegistrationModelRequest request) throws ServiceException {

        CompanyDto companyDto = new CompanyDto();
        companyDto.setName(request.getCompanyName());
        companyDto.setPhone(request.getPhone());
        Company company = companyService.create(companyDto);

        UserDto userDto = new UserDto();
        userDto.setPassword(request.getPassword());
        userDto.setEmail(request.getEmail());
        userDto.setCompanyId(company.getId());
        userDto.setName(request.getName());

        SignUpStatus signUpStatus = userService.signUp(userDto);
        return new ResponseStatus(signUpStatus.name());
    }

    @RequestMapping(value = "/validate", method = RequestMethod.POST)
    public ResponseStatus validate(UserValidationModelRequest request) {
        SignUpStatus signUpStatus = userService.validate(request.getEmail());
        return new ResponseStatus(signUpStatus.name());
    }
}
