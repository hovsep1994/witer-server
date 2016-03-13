package com.waiter.server.api.menu.model.request;

/**
 * User: hovsep
 * Date: 2/20/16
 * Time: 5:42 PM
 */
public class AddMenuRequest {

    private String name;

    private Long companyId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
}
