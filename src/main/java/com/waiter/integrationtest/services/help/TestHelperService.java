package com.waiter.integrationtest.services.help;

import com.waiter.server.services.category.dto.CategoryDto;
import com.waiter.server.services.category.model.Category;
import com.waiter.server.services.company.CompanyService;
import com.waiter.server.services.company.dto.CompanyDto;
import com.waiter.server.services.company.model.Company;
import com.waiter.server.services.currency.Currency;
import com.waiter.server.services.language.Language;
import com.waiter.server.services.menu.MenuService;
import com.waiter.server.services.menu.model.Menu;
import com.waiter.server.services.menu.model.MenuDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;

/**
 * Created by hovsep on 9/17/16.
 */
@Service
public class TestHelperService {

    @Autowired
    private MenuService menuService;

    @Autowired
    private CompanyService companyService;

    public Company createCompany() {
        return companyService.create(createCompanyDto());
    }

    public Menu createMenu() {
        Company company = createCompany();
        return menuService.create(company.getId(), createMenuDto());
    }

    public CategoryDto createCategoryDto() {
        final CategoryDto categoryDto = new CategoryDto();
        categoryDto.setLanguage(Language.en);
        categoryDto.setName("testCategory");
        categoryDto.setTags(new HashSet<>());
        return categoryDto;
    }

    public MenuDto createMenuDto() {
        final MenuDto menuDto = new MenuDto();
        menuDto.setLanguage(Language.en);
        menuDto.setName("testMenu");
        menuDto.setCurrency(Currency.USD);
        return menuDto;
    }

    public CompanyDto createCompanyDto() {
        final CompanyDto companyDto = new CompanyDto();
        companyDto.setName("testCompany");
        companyDto.setPhone("testPhone");
        return companyDto;
    }

}
