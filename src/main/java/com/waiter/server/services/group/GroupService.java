package com.waiter.server.services.group;

import com.waiter.server.commons.entities.Group;
import org.springframework.stereotype.Service;

/**
 * @author shahenpoghosyan
 */
@Service
public interface GroupService {

    Group create(Group group);
    void remove(Long groupId);
    void update(Group group);
    Group get(Long id);

}
