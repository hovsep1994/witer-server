package com.waiter.server.api.user;

import com.waiter.server.api.common.MainController;
import com.waiter.server.api.common.model.MenuKitResponseEntity;
import com.waiter.server.api.common.model.ResponseStatus;
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
import org.springframework.web.bind.annotation.*;

import static com.waiter.server.api.common.model.MenuKitResponseEntity.*;

/**
 * Created by Admin on 1/17/2016.
 */
@RestController
@RequestMapping("/users")
public class UserController extends MainController{

    @Autowired
    private UserService userService;

    @Autowired
    private CompanyService companyService;

    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    public MenuKitResponseEntity<UserModel> login(@RequestParam String email, @RequestParam String password) {
        User user = userService.signIn(email, password);
        UserModel userModel = new UserModel();
        userModel.setEmail(user.getEmail());
        userModel.setName(user.getName());
        userModel.setToken(user.getToken());
        return MenuKitResponseEntity.success2(userModel);
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public MenuKitResponseEntity<UserModel> register(@RequestBody UserRegistrationModelRequest request) throws ServiceException {
        CompanyDto companyDto = new CompanyDto();
        companyDto.setName(request.getCompanyName());
        companyDto.setPhone(request.getPhone());
        Company company = companyService.create(companyDto);
        UserDto userDto = new UserDto();
        userDto.setPassword(request.getPassword());
        userDto.setEmail(request.getEmail());
        userDto.setCompanyId(company.getId());
        userDto.setName(request.getName());
        User user = userService.signUp(userDto);

        UserModel userModel = new UserModel();
        userModel.updateProperties(user);
        return success2(userModel);
    }

    @RequestMapping(value = "/validate", method = RequestMethod.POST)
    public ResponseStatus validate(UserValidationModelRequest request) {
        SignUpStatus signUpStatus = userService.validate(request.getEmail());
        return new ResponseStatus(signUpStatus.name());
    }
}
