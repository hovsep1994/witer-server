package com.waiter.server.persistence.core.repository.company;

import com.waiter.server.services.company.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Admin on 12/23/2015.
 */
@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    List<Company> findCompanyByName(String name);
}
