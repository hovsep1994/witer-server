package com.waiter.server.api.group;

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

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Group> getGroupById(@PathVariable Long id) {
        return ResponseEntity.forResponse(groupService.get(id));
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<Group> addGroup(Group group) {
        Group createdGroup = groupService.create(group);
        return ResponseEntity.forResponse(createdGroup);
    }
}
