package com.waiter.server.services.name;

import com.waiter.server.services.name.model.Name;

import java.util.List;

/**
 * @author shahenpoghosyan
 */
public interface NameService {
    Name create(Name name);
    List<Name> create(List<Name> names);
}
