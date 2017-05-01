package com.waiter.server.web;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Company: SFL LLC
 * Created on 4/28/17
 *
 * @author Hovsep Harutyunyan
 */
@RestController
@RequestMapping("/")
public class YandexController {

    @RequestMapping(value = "c66a90755855.html", method = RequestMethod.GET)
    public String businessLanding(ModelMap model) {
        return "baf8ed6bad08";
    }

}
