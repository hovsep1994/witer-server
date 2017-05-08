package com.waiter.server.web;

import com.waiter.server.api.common.AuthenticationController;
import com.waiter.server.services.user.model.User;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author shahenpoghosyan
 */
@Controller
@RequestMapping("/business")
public class BusinessController extends AuthenticationController {

    private static final Logger LOGGER = Logger.getLogger(BusinessController.class);


    @RequestMapping(value = "/landing", method = RequestMethod.GET)
    public String businessLanding(ModelMap model) {
        return "/web/business/landing";
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String business(ModelMap model, @ModelAttribute User user) {


        model.addAttribute("user", user);
        LOGGER.info("Business controller - webiste2. ");
        return "/web/business/admin";
    }

    @RequestMapping(value = "/menu/*", method = RequestMethod.GET)
    public String menu(ModelMap model, @ModelAttribute User user) {
        model.addAttribute("user", user);
        LOGGER.info("Business controller - webiste2. ");
        return "/web/business/admin/menu";
    }

    @RequestMapping(value = "/menu/translation", method = RequestMethod.GET)
    public String menuTranslation(ModelMap model, @ModelAttribute User user) {
        model.addAttribute("user", user);
        LOGGER.info("Business controller - menu translation. ");
        return "/web/business/admin/menu/translation";
    }

    @RequestMapping(value = "/account/settings", method = RequestMethod.GET)
    public String accountSettings(ModelMap model, @ModelAttribute User user) {
        model.addAttribute("user", user);
        LOGGER.info("Business controller - account settings. ");
        return "/web/account/settings";
    }
}
