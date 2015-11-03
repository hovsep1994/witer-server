package com.waiter.server.db;

import com.waiter.server.commons.entities.Group;

import java.util.List;

public interface GroupDAO {
    int create(Group group);
    Group get(int id);
}
