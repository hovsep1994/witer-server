package com.waiter.server.services.company;

import com.waiter.server.services.company.dto.CompanyDto;
import com.waiter.server.services.company.model.Company;

import java.util.List;

/**
 * Created by Admin on 12/15/2015.
 */
public interface CompanyService {

    Company create(CompanyDto companyDto);

    Company get(Long id);

    List<Company> search(String s);

}
