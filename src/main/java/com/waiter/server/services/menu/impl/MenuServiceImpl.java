package com.waiter.server.services.menu.impl;

import com.waiter.server.persistence.core.repository.menu.MenuRepository;
import com.waiter.server.services.common.exception.ServiceRuntimeException;
import com.waiter.server.services.menu.MenuService;
import com.waiter.server.services.menu.model.Menu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    @Override
    public Menu create(Menu menu) {
        Assert.notNull(menu, "menu must not be null");
        Assert.isNull(menu.getId(), "menu id must be null");
        Menu createdMenu = menuRepository.save(menu);
        return createdMenu;
    }

    @Override
    public Menu get(Long id) {
        Assert.notNull(id, "id must not be null");
        Menu menu = menuRepository.findOne(id);
        if (menu == null) {
            LOGGER.debug("Product with id -{} not found", id);
            throw new ServiceRuntimeException("Product not found");
        }
        return menu;
    }

    @Override
    public List<Menu> getCompanyMenus(Long companyId) {
        Assert.notNull(companyId, "companyId must not be null");
        List<Menu> menus = menuRepository.findByCompanyId(companyId);
        return menus;
    }

    public Menu update(Menu menu) {
        Assert.notNull(menu, "menu must not be null");
        Assert.notNull(menu.getId(), "menu id must not be null");
        /*
         * checking does menu with id exist
         */
        get(menu.getId());
        menu.setUpdated(new Date());
        Menu updatedMenu = menuRepository.save(menu);
        return updatedMenu;
    }

    @Override
    public void remove(Long id) {
        Assert.notNull(id, "menu id must not be null");
        menuRepository.delete(id);
    }

}
