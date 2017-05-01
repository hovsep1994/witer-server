package com.waiter.server.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author shahenpoghosyan
 */
@Controller
@RequestMapping("/venues")
public class VenueController {

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String businessLanding(ModelMap model, @PathVariable("id") String id) {
        return "/web/venues/";
    }
}
