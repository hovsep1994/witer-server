package com.waiter.server.api;

import com.waiter.server.api.common.ResponseEntity;
import com.waiter.server.services.group.GroupService;
import com.waiter.server.services.group.model.Group;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author shahenpoghosyan
 */
@RestController
@RequestMapping("/groups")
public class GroupController {

    private static final Logger logger = Logger.getLogger(GroupController.class);

    @Autowired
    private GroupService groupService;

    @RequestMapping(method = RequestMethod.POST)
    public void create(@RequestPart("group") Group group) {
        groupService.create(group); //TODO handle image
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Group> findOne(@PathVariable Long id) {
        return new ResponseEntity<>(groupService.get(id));
    }

}
