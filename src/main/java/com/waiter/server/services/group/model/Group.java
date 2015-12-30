package com.waiter.server.services.group.model;


import com.waiter.server.services.common.model.AbstractEntityModel;
import com.waiter.server.services.menu.model.Menu;
import com.waiter.server.services.name.model.Name;
import com.waiter.server.services.product.model.Product;
import com.waiter.server.services.tag.model.Tag;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

/**
 * Created by Admin on 12/23/2015.
 */
@Entity
@Table(name = "table")
public class Group extends AbstractEntityModel {

    private List<Name> names;
    private String image;
    private List<Product> products;
    private List<Tag> tags;
    private Menu menu;


}
