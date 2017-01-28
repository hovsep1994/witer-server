package com.waiter.server.services.category.event;

import com.waiter.server.services.event.ApplicationEvent;
import com.waiter.server.services.event.ApplicationEventListener;

/**
 * Created by hovsep on 8/5/16.
 */
public abstract class CategoryUpdateEventListener implements ApplicationEventListener {

    @Override
    public void process(ApplicationEvent applicationEvent) {
        if (applicationEvent instanceof CategoryUpdateEvent) {
            final CategoryUpdateEvent event = (CategoryUpdateEvent) applicationEvent;
            processProductUpdatedEvent(event);
        }
    }

    public abstract void processProductUpdatedEvent(CategoryUpdateEvent categoryUpdateEvent);

}
