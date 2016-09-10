package com.waiter.server.services.menu;


import com.waiter.server.services.company.model.Company;
import com.waiter.server.services.currency.Currency;
import com.waiter.server.services.language.Language;
import com.waiter.server.services.menu.model.Menu;
import com.waiter.server.services.menu.model.MenuDto;

import java.util.List;

/**
 * Created by Admin on 12/12/2015.
 */
public interface MenuService {

    Menu getById(Long id);

    Menu create(Long companyId, MenuDto menuDto);

    Menu update(Long menuId, MenuDto menuDto);

    Menu addLanguage(Long menuId, Language language);

    List<Menu> getMenusByCompanyId(Long companyId);

    Company getCompanyByMenuId(Long menuId);

    void delete(Long id);
}
