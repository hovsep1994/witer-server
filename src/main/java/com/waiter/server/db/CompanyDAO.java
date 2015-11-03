package com.waiter.server.db;

import com.waiter.server.commons.APIException;
import com.waiter.server.commons.entities.Company;

import java.util.List;

/**
 * Created by Admin on 10/24/2015.
 */
public interface CompanyDAO {
    int create(Company company);
    Company authenticate(String key) throws APIException;
    Company get(int id);
    List<Company> search(String s);
    boolean validateEmail(String hash);
}
