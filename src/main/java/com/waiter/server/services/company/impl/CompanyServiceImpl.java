package com.waiter.server.services.company.impl;

import com.waiter.server.persistence.core.repository.company.CompanyRepository;
import com.waiter.server.services.company.CompanyService;
import com.waiter.server.services.company.dto.CompanyDto;
import com.waiter.server.services.company.model.Company;
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
    public Company create(CompanyDto companyDto) {
        Company company = new Company();
        return companyRepository.save(company);
    }

    @Override
    public Company get(Long id) {
        return companyRepository.findOne(id);
    }

    @Override
    public List<Company> search(String name) {
        return companyRepository.findCompanyByName(name);
    }
}
