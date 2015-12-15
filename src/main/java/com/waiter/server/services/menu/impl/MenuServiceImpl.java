package com.waiter.server.services.menu.impl;

import com.waiter.server.commons.entities.Menu;
import com.waiter.server.repository.sql.MenuRepository;
import com.waiter.server.services.menu.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Admin on 12/12/2015.
 */

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuRepository menuRepository;


    @Override
    public Menu create(Menu menu) {
        return menuRepository.create(menu);
    }

    @Override
    public Menu get(Long id) {
        return menuRepository.get(id.intValue());
    }

    @Override
    public List<Menu> getCompanyMenus(Long companyId) {
        return menuRepository.getCompanyMenus(companyId.intValue());
    }

}
