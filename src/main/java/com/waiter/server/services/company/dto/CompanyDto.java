package com.waiter.server.services.company.dto;

import com.waiter.server.services.common.model.AbstractDtoModel;
import com.waiter.server.services.company.model.Company;

/**
 * @author shahenpoghosyan
 */
public class CompanyDto extends AbstractDtoModel<Company>{

    private String name;
    private String phone;

    @Override
    public void updateProperties(Company company) {
        company.setPhone(getPhone());
        company.setName(getName());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
