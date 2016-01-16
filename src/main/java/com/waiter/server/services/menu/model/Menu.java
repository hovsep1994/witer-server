package com.waiter.server.services.menu.model;

import com.waiter.server.services.common.model.AbstractNamedEntityModel;
import com.waiter.server.services.company.model.Company;
import com.waiter.server.services.group.model.Group;

import javax.persistence.*;
import java.util.List;

/**
 * @author shahenpoghosyan
 */
@Entity
@Table(name = "menu")
public class Menu extends AbstractNamedEntityModel {

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Group> groups;

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }
}
