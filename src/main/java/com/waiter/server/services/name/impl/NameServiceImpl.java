package com.waiter.server.services.name.impl;

import com.waiter.server.persistence.core.repository.name.NameRepository;
import com.waiter.server.services.name.NameService;
import com.waiter.server.services.name.model.Name;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author shahenpoghosyan
 */
@Service
public class NameServiceImpl implements NameService{

    @Autowired
    private NameRepository nameRepository;

    @Override
    public Name create(Name name) {
        return nameRepository.save(name);
    }

    @Override
    public List<Name> create(List<Name> names) {
        return nameRepository.save(names);
    }
}
