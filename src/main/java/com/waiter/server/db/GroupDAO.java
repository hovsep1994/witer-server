package com.waiter.server.db;

import com.waiter.server.commons.entities.Group;

import java.util.List;

/**
 * @author shahenpoghosyan
 */
public interface GroupDAO {
    int create(Group group);
    Group get(int id);
}
