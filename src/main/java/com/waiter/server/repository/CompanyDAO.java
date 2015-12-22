package com.waiter.server.repository;

import com.waiter.server.commons.entities.Company;

import java.util.List;

/**
 * Created by Admin on 10/24/2015.
 */
public interface CompanyDAO {
    Company create(Company company);
    Company get(int id);
    List<Company> search(String s);
}
