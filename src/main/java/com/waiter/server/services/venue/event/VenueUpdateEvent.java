package com.waiter.server.services.venue.event;

import com.waiter.server.services.event.ApplicationEvent;

/**
 * Created by hovsep on 8/5/16.
 */
public class VenueUpdateEvent implements ApplicationEvent {

    private Long venueId;

    public VenueUpdateEvent(Long venueId) {
        this.venueId = venueId;
    }

    public Long getVenueId() {
        return venueId;
    }
}
