package com.waiter.server.api.common;

import com.waiter.server.services.user.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by hovsep on 7/2/16.
 */
@Controller
public class AuthenticationController extends MainController {

    @ModelAttribute("user")
    public User getUser(HttpServletRequest request) {
        return (User) request.getAttribute("user");
    }

}
