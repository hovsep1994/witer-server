package com.waiter.server.api;

import com.waiter.server.api.common.ResponseEntity;
import com.waiter.server.commons.entities.Group;
import com.waiter.server.services.group.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author shahenpoghosyan
 */
@RestController
@RequestMapping("/groups")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @RequestMapping(method = RequestMethod.POST)
    public void create(@RequestPart("image") MultipartFile imagePart, @RequestPart("group") Group group) {
        groupService.create(group); //TODO handle image
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Group> findOne(@PathVariable Long id) {
        return new ResponseEntity<>(groupService.get(id));
    }

}
