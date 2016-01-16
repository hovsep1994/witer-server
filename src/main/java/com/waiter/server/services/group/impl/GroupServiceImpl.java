package com.waiter.server.services.group.impl;

import com.waiter.server.persistence.core.repository.group.GroupRepository;
import com.waiter.server.services.group.GroupService;
import com.waiter.server.services.group.model.Group;
import com.waiter.server.services.tag.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author shahenpoghosyan
 */
@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private TagService tagService;

    @Override
    public Group create(Group group) {
        Assert.notNull(group, "Group must nut be null");
        Group createdGroup = groupRepository.save(group);
        return createdGroup;
    }

    @Override
    public void remove(Long groupId) {
        groupRepository.delete(groupId);
    }

    @Override
    public void update(Group group) {
        groupRepository.save(group);
    }

    @Override
    public Group get(Long id) {
        return groupRepository.findOne(id);
    }
}
