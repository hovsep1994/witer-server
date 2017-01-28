package com.waiter.server.services.venue.event;

import com.waiter.server.services.event.ApplicationEvent;
import com.waiter.server.services.event.ApplicationEventListener;

/**
 * Created by hovsep on 8/5/16.
 */
public abstract class VenueLocationUpdateEventListener implements ApplicationEventListener {

    @Override
    public void process(ApplicationEvent applicationEvent) {
        if (applicationEvent instanceof VenueLocationUpdateEvent) {
            processVenueLocationUpdatedEvent((VenueLocationUpdateEvent) applicationEvent);
        }
    }

    public abstract void processVenueLocationUpdatedEvent(VenueLocationUpdateEvent venueLocationUpdateEvent);

}
