package com.waiter.server.web;

import com.waiter.server.api.common.model.ResponseEntity;
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
    public ResponseEntity<String> heartbeat() {
        return ResponseEntity.success("ok");
    }

}
