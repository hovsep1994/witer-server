package com.waiter.server.services.product.event;

import com.waiter.server.services.event.ApplicationEvent;
import com.waiter.server.services.event.ApplicationEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by hovsep on 8/5/16.
 */
public abstract class ProductUpdateEventListener implements ApplicationEventListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductUpdateEventListener.class);


    @Override
    public void process(ApplicationEvent applicationEvent) {
        if (applicationEvent instanceof ProductUpdateEvent) {
            final ProductUpdateEvent event = (ProductUpdateEvent) applicationEvent;
            try {
                processProductUpdatedEvent(event);
            } catch (Exception e) {
                LOGGER.error("Error processing product update event. ", e);
            }
        }
    }

    public abstract void processProductUpdatedEvent(ProductUpdateEvent productUpdateEvent);

}
