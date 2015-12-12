package com.waiter.server.services.group.impl;

import com.waiter.server.commons.entities.Group;
import com.waiter.server.repository.sql.BaseRepository;
import com.waiter.server.repository.sql.GroupRepository;
import com.waiter.server.services.group.GroupService;
import com.waiter.server.services.tag.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        Group createdGroup = groupRepository.create(group);
        List<Integer> tagIds = tagService.insertAndGetIds(group.getTags());
        groupRepository.insertMappings(BaseRepository.MappingTable.GROUP_TAG_MAP, group.getId(), tagIds);
        return createdGroup;
    }

    @Override
    public void remove(Long groupId) {
        groupRepository.remove(groupId.intValue());
    }

    @Override
    public void update(Group group) {
        groupRepository.update(group);
    }

    @Override
    public Group get(Long id) {
        return groupRepository.get(id.intValue());
    }
}
