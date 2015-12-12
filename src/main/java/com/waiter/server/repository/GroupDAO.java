package com.waiter.server.repository;

import com.waiter.server.commons.entities.Group;

public interface GroupDAO {
    Group create(Group group);
    void remove(int groupId);
    void update(Group group);
    Group get(int id);
}
