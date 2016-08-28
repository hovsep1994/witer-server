package com.waiter.server.services.menu;


import com.waiter.server.services.company.model.Company;
import com.waiter.server.services.currency.Currency;
import com.waiter.server.services.language.Language;
import com.waiter.server.services.menu.model.Menu;

import java.util.List;

/**
 * Created by Admin on 12/12/2015.
 */
public interface MenuService {

    Menu getById(Long id);

    Menu create(String menuName, Language language, Currency currency, Long companyId);

    Menu update(Long menuId, String name, Language mainLanguage, Currency currency);

    Menu addLanguage(Long menuId, Language language);

    Menu update(Menu menu);

    List<Menu> getMenusByCompanyId(Long companyId);

    Company getCompanyByMenuId(Long menuId);

    void delete(Long id);
}
