package com.waiter.server.db;

import com.waiter.server.commons.entities.Menu;

import java.util.List;

public interface MenuDAO {
    int create(Menu menu);
    Menu get(int id);
    List<Menu> getCompanyMenus(int companyId);

}
