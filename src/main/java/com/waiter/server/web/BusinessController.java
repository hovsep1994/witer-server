package com.waiter.server.web;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author shahenpoghosyan
 */
@Controller
@RequestMapping("/business")
public class BusinessController {

    private static final Logger logger = Logger.getLogger(BusinessController.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String business(ModelMap model) {
        model.addAttribute("user", "valod");
        logger.info("Business controller - webiste2. ");
        return "/web/business/admin";
    }

    @RequestMapping(value = "/menu", method = RequestMethod.GET)
    public String menu(ModelMap model) {
        model.addAttribute("user", "valod");
        logger.info("Business controller - webiste2. ");
        return "/web/business/admin/menu";
    }

    @RequestMapping(value = "/menu/translation", method = RequestMethod.GET)
    public String menuTranslation(ModelMap model) {
        model.addAttribute("user", "valod");
        logger.info("Business controller - menu translation. ");
        return "/web/business/admin/menu/translation";
    }

    @RequestMapping(value = "/account/settings", method = RequestMethod.GET)
    public String accountSettings(ModelMap model) {
        model.addAttribute("user", "valod");
        logger.info("Business controller - account settings. ");
        return "/web/account/settings";
    }
}
