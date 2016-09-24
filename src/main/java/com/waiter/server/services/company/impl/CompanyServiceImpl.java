package com.waiter.server.services.company.impl;

import com.waiter.server.persistence.core.repository.company.CompanyRepository;
import com.waiter.server.services.common.exception.ErrorCode;
import com.waiter.server.services.common.exception.ServiceRuntimeException;
import com.waiter.server.services.company.CompanyService;
import com.waiter.server.services.company.dto.CompanyDto;
import com.waiter.server.services.company.model.Company;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Created by Admin on 12/15/2015.
 */
@Service
public class CompanyServiceImpl implements CompanyService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyServiceImpl.class);

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public Company create(CompanyDto companyDto) {
        assertCompanyDto(companyDto);
        Company company = new Company();
        companyDto.updateProperties(company);
        return companyRepository.save(company);
    }

    @Override
    public Company get(Long id) {
        final Company company = companyRepository.findOne(id);
        if (company == null) {
            LOGGER.error("Company with id -{} not found", id);
            throw new ServiceRuntimeException(ErrorCode.NOT_FOUND, "Company not found");
        }
        return company;
    }

    @Override
    public List<Company> search(String name) {
        return null;
    }

    private void assertCompanyDto(CompanyDto companyDto) {
        Assert.notNull(companyDto);
        Assert.notNull(companyDto.getName());
    }
}
