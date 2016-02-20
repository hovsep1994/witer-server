package com.waiter.server.api.menu.model.request;

/**
 * User: hovsep
 * Company: SFL LLC
 * Date: 2/20/16
 * Time: 5:42 PM
 */
public class AddMenuRequest {

    private String name;

    private String companyId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
}
