package com.waiter.server.services.venue.event;

import com.waiter.server.services.event.ApplicationEvent;
import com.waiter.server.services.event.ApplicationEventListener;

/**
 * Created by hovsep on 8/5/16.
 */
public abstract class VenueUpdateEventListener implements ApplicationEventListener {

    @Override
    public void process(ApplicationEvent applicationEvent) {
        if (applicationEvent instanceof VenueLocationUpdateEvent) {
            processVenueUpdatedEvent((VenueLocationUpdateEvent) applicationEvent);
        }
    }

    public abstract void processVenueUpdatedEvent(VenueLocationUpdateEvent venueLocationUpdateEvent);

}
