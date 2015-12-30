package com.waiter.server.services.user.model;

import com.waiter.server.services.common.model.AbstractEntityModel;
import com.waiter.server.services.company.model.Company;

public class User extends AbstractEntityModel {

    private String name;
    private String email;
    private String password;
    private String token;
    private String hash;
    private Company company;
}
