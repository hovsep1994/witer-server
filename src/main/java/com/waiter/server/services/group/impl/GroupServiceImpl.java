package com.waiter.server.services.group.impl;

import com.waiter.server.persistence.core.repository.group.GroupRepository;
import com.waiter.server.services.common.exception.ServiceRuntimeException;
import com.waiter.server.services.group.GroupService;
import com.waiter.server.services.group.model.Group;
import com.waiter.server.services.tag.TagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

/**
 * @author shahenpoghosyan
 */
@Service
public class GroupServiceImpl implements GroupService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GroupServiceImpl.class);

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
    public Group update(Group group) {
        Assert.notNull(group, "group must not be null");
        Assert.notNull(group.getId(), "group id must not be null");
        /*
         * checking does product with id exist
         */
        get(group.getId());
        group.setUpdated(new Date());
        Group updatedGroup = groupRepository.save(group);
        return updatedGroup;
    }

    @Override
    public Group get(Long id) {
        Group group = groupRepository.findOne(id);
        if (group == null) {
            LOGGER.debug("group with id -{} not found", id);
            throw new ServiceRuntimeException("group not found");
        }
        return group;
    }
}
