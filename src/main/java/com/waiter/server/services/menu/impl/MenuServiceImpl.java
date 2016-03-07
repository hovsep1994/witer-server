package com.waiter.server.services.menu.impl;

import com.waiter.server.persistence.core.repository.menu.MenuRepository;
import com.waiter.server.services.common.exception.ErrorCode;
import com.waiter.server.services.common.exception.ServiceRuntimeException;
import com.waiter.server.services.company.CompanyService;
import com.waiter.server.services.company.model.Company;
import com.waiter.server.services.menu.MenuService;
import com.waiter.server.services.menu.model.Menu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

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
    @Transactional
    public Menu create(String menuName, Long companyId) {
        assertName(menuName);
        assertCompanyId(companyId);
        Menu menu = new Menu();
        Company company = companyService.get(companyId);
        menu.setCompany(company);
        menu.setName(menuName);
        return menuRepository.save(menu);
    }

    @Override
    public Menu getById(Long id) {
        Assert.notNull(id, "id must not be null");
        Menu menu = menuRepository.findOne(id);
        if (menu == null) {
            LOGGER.debug("Product with id -{} not found", id);
            throw new ServiceRuntimeException(ErrorCode.NOT_FOUND, "Product not found");
        }
        return menu;
    }

    @Override
    public List<Menu> getMenusByCompanyId(Long companyId) {
        Assert.notNull(companyId, "companyId must not be null");
        return menuRepository.findByCompanyId(companyId);
    }


    @Override
    @Transactional
    public Menu update(Long menuId, String menuName) {
        assertMenuId(menuId);
        assertName(menuName);
        Menu menu = getById(menuId);
        menu.setUpdated(new Date());
        menu.setName(menuName);
        return menuRepository.save(menu);
    }

    @Override
    @Transactional
    public void remove(Long menuId) {
        assertMenuId(menuId);
        Menu menu = getById(menuId);
        menuRepository.delete(menu);
    }

    private void assertMenuId(Long id) {
        Assert.notNull(id, "menu id must not be null");
    }

    private void assertCompanyId(Long companyId) {
        Assert.notNull(companyId, "company id not must be null");
    }

    private void assertName(String name) {
        Assert.notNull(name, "menu name must not be null");
    }

}
