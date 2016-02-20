package com.waiter.server.services.common.model;

/**
 * User: hovsep
 * Company: SFL LLC
 * Date: 2/20/16
 * Time: 6:07 PM
 */
public abstract class AbstractDtoModel<T extends AbstractEntityModel> {

    public abstract void convertToEntityModel(T t);

}
