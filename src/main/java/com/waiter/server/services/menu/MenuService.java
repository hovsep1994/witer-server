package com.waiter.server.services.menu;

import com.waiter.server.commons.entities.Menu;

import java.util.List;

/**
 * Created by Admin on 12/12/2015.
 */
public interface MenuService {

    Menu create(Menu menu);

    Menu get(Long id);

    List<Menu> getCompanyMenus(Long companyId);
}
