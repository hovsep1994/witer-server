package com.waiter.server.db;

import com.waiter.server.commons.entities.Menu;

public interface MenuDAO {
    int create(Menu menu);
    Menu get(int id);
}
