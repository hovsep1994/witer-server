package com.waiter.server.services.user.dto;

import com.waiter.server.services.common.model.AbstractDtoModel;
import com.waiter.server.services.company.model.Company;
import com.waiter.server.services.user.model.User;

/**
 * User: hovsep
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
        Company company = new Company();
        company.setId(getCompanyId());
        user.setName(getName());
        user.setEmail(getEmail());
        user.setPassword(getPassword());
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDto)) return false;

        UserDto userDto = (UserDto) o;

        if (name != null ? !name.equals(userDto.name) : userDto.name != null) return false;
        if (email != null ? !email.equals(userDto.email) : userDto.email != null) return false;
        if (password != null ? !password.equals(userDto.password) : userDto.password != null) return false;
        return !(companyId != null ? !companyId.equals(userDto.companyId) : userDto.companyId != null);

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (companyId != null ? companyId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", companyId=" + companyId +
                '}';
    }
}
