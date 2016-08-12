package com.waiter.server.services.venue.event;

import com.waiter.server.services.event.ApplicationEvent;

/**
 * Created by hovsep on 8/5/16.
 */
public class VenueLocationUpdateEvent implements ApplicationEvent {

    private Long venueId;

    public VenueLocationUpdateEvent(Long venueId) {
        this.venueId = venueId;
    }

    public Long getVenueId() {
        return venueId;
    }
}
