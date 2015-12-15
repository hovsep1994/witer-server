package com.waiter.server.repository;

import com.waiter.server.commons.entities.Menu;

import java.util.List;

public interface MenuDAO {
    Menu create(Menu menu);
    Menu get(int id);
    List<Menu> getCompanyMenus(int companyId);

}
