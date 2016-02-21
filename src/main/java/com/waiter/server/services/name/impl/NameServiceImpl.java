package com.waiter.server.services.name.impl;

import com.waiter.server.persistence.core.repository.name.NameRepository;
import com.waiter.server.services.name.NameService;
import com.waiter.server.services.name.model.NameTranslation;
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
    public NameTranslation create(NameTranslation nameTranslation) {
        return nameRepository.save(nameTranslation);
    }

    @Override
    public List<NameTranslation> create(List<NameTranslation> nameTranslations) {
        return nameRepository.save(nameTranslations);
    }
}
