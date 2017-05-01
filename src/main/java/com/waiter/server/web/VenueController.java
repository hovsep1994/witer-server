package com.waiter.server.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author shahenpoghosyan
 */
@Controller
@RequestMapping("/v")
public class VenueController {

    @RequestMapping(value = "/**", method = RequestMethod.GET)
    public String businessLanding(ModelMap model) {
        return "/web/venues/";
    }
}
