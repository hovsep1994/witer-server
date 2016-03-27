package com.waiter.server.services.common.model;

/**
 * User: hovsep
 * Date: 2/20/16
 * Time: 6:07 PM
 */
public abstract class AbstractDtoModel<T extends AbstractEntityModel> {

    public abstract void updateProperties(T t);

}
