package com.waiter.server.services.menu;


import com.waiter.server.services.menu.model.Menu;

import java.util.List;

/**
 * Created by Admin on 12/12/2015.
 */
public interface MenuService {

    Menu create(String menuName, Long companyId);

    Menu getById(Long id);

    List<Menu> getMenusByCompanyId(Long companyId);

    Menu update(Long menuId, String menuName);

    void remove(Long id);
}
