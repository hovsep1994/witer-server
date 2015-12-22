package com.waiter.server.services.company;

import com.waiter.server.commons.entities.Company;

import java.util.List;

/**
 * Created by Admin on 12/15/2015.
 */
public interface CompanyService {

    Company create(Company company);

    Company get(Long id);

    List<Company> search(String s);

}
