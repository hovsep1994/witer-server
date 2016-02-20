package com.waiter.server.services.user.dto;

import com.waiter.server.services.common.model.AbstractDtoModel;
import com.waiter.server.services.company.model.Company;
import com.waiter.server.services.user.model.User;

/**
 * User: hovsep
 * Company: SFL LLC
 * Date: 2/20/16
 * Time: 6:06 PM
 */
public class UserDto extends AbstractDtoModel<User>{

    private String name;
    private String email;
    private String password;
    private Long companyId;

    @Override
    public void convertToEntityModel(User user) {
        user.setName(getName());
        user.setEmail(getEmail());
        user.setPassword(getPassword());
        Company company = new Company();
        company.setId(getCompanyId());
        user.setCompany(company);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
}
