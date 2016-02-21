package com.waiter.server.services.company.dto;

import com.waiter.server.services.common.model.AbstractDtoModel;
import com.waiter.server.services.company.model.Company;
import com.waiter.server.services.language.Language;
import com.waiter.server.services.name.model.NameTranslation;

/**
 * @author shahenpoghosyan
 */
public class CompanyDto extends AbstractDtoModel<Company>{

    private String name;
    private String language;
    private String phone;

    @Override
    public void convertToEntityModel(Company company) {
        company.setPhone(getPhone());
        NameTranslation nameTranslation = new NameTranslation();
        nameTranslation.setLanguage(Language.valueOf(getLanguage()));
        company.setNameTranslation(nameTranslation);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
