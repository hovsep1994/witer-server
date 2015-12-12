package com.waiter.server.services.name.impl;

import com.waiter.server.commons.entities.Name;
import com.waiter.server.repository.sql.NameRepository;
import com.waiter.server.services.name.NameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author shahenpoghosyan
 */
@Service
public class NameServiceImpl implements NameService{

    @Autowired
    private NameRepository nameRepository;

    @Override
    public Name create(Name name) {
        return nameRepository.create(name);
    }
}
