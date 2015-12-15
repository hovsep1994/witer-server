package com.waiter.server.services.company.impl;

import com.waiter.server.commons.entities.Company;
import com.waiter.server.repository.sql.CompanyRepository;
import com.waiter.server.services.company.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Admin on 12/15/2015.
 */
@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public Company create(Company company) {
        return companyRepository.create(company);
    }

    @Override
    public Company get(Long id) {
        return companyRepository.get(id.intValue());
    }

    @Override
    public List<Company> search(String s) {
        return companyRepository.search(s);
    }
}
