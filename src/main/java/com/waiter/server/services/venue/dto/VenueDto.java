package com.waiter.server.services.venue.dto;

import com.waiter.server.services.common.model.AbstractDtoModel;
import com.waiter.server.services.company.model.Company;
import com.waiter.server.services.location.model.Location;
import com.waiter.server.services.menu.model.Menu;
import com.waiter.server.services.venue.model.Venue;

/**
 * Created by hovsep on 3/5/16.
 */
public class VenueDto extends AbstractDtoModel<Venue> {

    private String name;
    private Location location;
    private Long companyId;
    private Long menuId;

    @Override
    public void convertToEntityModel(Venue venue) {
        Company company = new Company();
        company.setId(getCompanyId());
        Menu menu = new Menu();
        menu.setId(getMenuId());

        venue.setName(getName());
        venue.setMenu(menu);
        venue.setCompany(company);
        venue.setLocation(getLocation());
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
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
