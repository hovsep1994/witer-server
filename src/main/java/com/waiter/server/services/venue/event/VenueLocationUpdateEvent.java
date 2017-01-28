package com.waiter.server.services.venue.event;

import com.waiter.server.services.event.ApplicationEvent;
import com.waiter.server.services.venue.model.Venue;

/**
 * Created by hovsep on 8/5/16.
 */
public class VenueLocationUpdateEvent implements ApplicationEvent {

    private Venue venue;

    public VenueLocationUpdateEvent(Venue venue) {
        this.venue = venue;
    }

    public Venue getVenue() {
        return venue;
    }
}
