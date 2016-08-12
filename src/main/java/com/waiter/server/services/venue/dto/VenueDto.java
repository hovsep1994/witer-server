package com.waiter.server.services.venue.dto;

import com.waiter.server.services.common.model.AbstractDtoModel;
import com.waiter.server.services.company.model.Company;
import com.waiter.server.services.menu.model.Menu;
import com.waiter.server.services.venue.model.Venue;

/**
 * Created by hovsep on 3/5/16.
 */
public class VenueDto extends AbstractDtoModel<Venue> {

    private String name;
    private Long companyId;
    private Long menuId;

    @Override
    public void updateProperties(Venue venue) {
        Company company = new Company();
        company.setId(getCompanyId());
        if (menuId != null) {
            Menu menu = new Menu();
            menu.setId(getMenuId());
            venue.setMenu(menu);
        }
        venue.setName(getName());
        venue.setCompany(company);
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
