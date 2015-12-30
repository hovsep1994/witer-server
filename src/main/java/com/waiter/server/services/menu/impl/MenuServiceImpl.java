package com.waiter.server.services.menu.impl;

import com.waiter.server.persistence.core.repository.menu.MenuRepository;
import com.waiter.server.services.menu.MenuService;
import com.waiter.server.services.menu.model.Menu;
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
        return menuRepository.save(menu);
    }

    @Override
    public Menu get(Long id) {
        return menuRepository.findOne(id);
    }

    @Override
    public List<Menu> getCompanyMenus(Long companyId) {
        return menuRepository.findByCompanyId(companyId);
    }

}
