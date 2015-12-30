package com.waiter.server.services.menu.model;

import com.waiter.server.services.common.model.AbstractEntityModel;
import com.waiter.server.services.common.model.AbstractNamedEntityModel;
import com.waiter.server.services.company.model.Company;
import com.waiter.server.services.group.model.Group;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

/**
 * @author shahenpoghosyan
 */
@Entity
@Table(name = "menu")
public class Menu extends AbstractNamedEntityModel {

    private Company company;
    private List<Group> groups;

}
