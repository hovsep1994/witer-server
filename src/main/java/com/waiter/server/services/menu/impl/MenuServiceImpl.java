package com.waiter.server.services.menu.impl;

import com.waiter.server.persistence.core.repository.menu.MenuRepository;
import com.waiter.server.services.common.exception.ErrorCode;
import com.waiter.server.services.common.exception.ServiceRuntimeException;
import com.waiter.server.services.company.CompanyService;
import com.waiter.server.services.company.model.Company;
import com.waiter.server.services.currency.Currency;
import com.waiter.server.services.language.Language;
import com.waiter.server.services.menu.MenuService;
import com.waiter.server.services.menu.model.Menu;
import com.waiter.server.services.menu.model.MenuDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

import static org.springframework.util.Assert.notNull;

/**
 * Created by Admin on 12/12/2015.
 */

@Service
public class MenuServiceImpl implements MenuService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MenuServiceImpl.class);

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private CompanyService companyService;

    @Override
    public Menu getById(Long id) {
        notNull(id, "id must not be null");
        Menu menu = menuRepository.findOne(id);
        if (menu == null) {
            LOGGER.debug("Product with id -{} not found", id);
            throw new ServiceRuntimeException(ErrorCode.NOT_FOUND, "Product not found");
        }
        return menu;
    }

    @Override
    @Transactional
    public Menu create(Long companyId, MenuDto menuDto) {
        assertName(menuDto.getName());
        notNull(menuDto.getLanguage());
        assertCompanyId(companyId);
        final Menu menu = new Menu();
        menuDto.updateProperties(menu);
        final Company company = companyService.get(companyId);
        menu.setCompany(company);
        return menuRepository.save(menu);
    }

    @Override
    @Transactional
    public Menu update(Long menuId, MenuDto menuDto) {
        assertMenuId(menuId);
        final Menu menu = getById(menuId);
        menuDto.updateProperties(menu);
        menu.setUpdated(new Date());
        return menuRepository.save(menu);
    }

    @Override
    public Menu addLanguage(Long menuId, Language language) {
        notNull(menuId);
        notNull(language);
        final Menu menu = getById(menuId);
        menu.getLanguages().add(language);
        return menuRepository.save(menu);
    }


    @Override
    public List<Menu> getMenusByCompanyId(Long companyId) {
        notNull(companyId, "companyId must not be null");
        return menuRepository.findByCompanyId(companyId);
    }

    @Override
    public Company getCompanyByMenuId(Long menuId) {
        assertMenuId(menuId);
        return getById(menuId).getCompany();
    }

    @Override
    @Transactional
    public void delete(Long menuId) {
        assertMenuId(menuId);
        final Menu menu = getById(menuId);
        if (menu.getVenues() == null || menu.getVenues().isEmpty()) {
            LOGGER.error("Menu -{} can not be deleted", menu.getId());
            throw new ServiceRuntimeException(ErrorCode.CAN_NOT_BE_DELETED, "Menu is attached to venue");
        }
        menuRepository.delete(menu);
    }

    private void assertMenuId(Long id) {
        notNull(id, "menu id must not be null");
    }

    private void assertCompanyId(Long companyId) {
        notNull(companyId, "company id not must be null");
    }

    private void assertName(String name) {
        notNull(name, "menu name must not be null");
    }

}
