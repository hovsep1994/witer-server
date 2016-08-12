package com.waiter.server.services.product.event;

import com.waiter.server.services.event.ApplicationEvent;
import com.waiter.server.services.event.ApplicationEventListener;

/**
 * Created by hovsep on 8/5/16.
 */
public abstract class ProductUpdateEventListener implements ApplicationEventListener {

    @Override
    public void process(ApplicationEvent applicationEvent) {
        if (applicationEvent instanceof ProductUpdateEvent) {
            final ProductUpdateEvent event = (ProductUpdateEvent) applicationEvent;
            processProductUpdatedEvent(event);
        }
    }

    public abstract void processProductUpdatedEvent(ProductUpdateEvent productUpdateEvent);

}
