package com.waiter.server.web;

import com.waiter.server.api.common.model.MenuKitResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Admin on 1/17/2016.
 */
@RestController
@RequestMapping("/ok")
public class WebHeartBeatController {

    @RequestMapping(method = RequestMethod.GET)
    public MenuKitResponseEntity<String> heartbeat() {
        return MenuKitResponseEntity.success2("ok");
    }

}
