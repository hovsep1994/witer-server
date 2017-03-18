package com.waiter.server.services.venue.model;

import com.waiter.server.services.common.model.AbstractEntityModel;

import javax.persistence.*;
import java.time.DayOfWeek;

/**
 * @author shahenpoghosyan
 */
@Entity
@Table(name = "schedule")
public class Schedule extends AbstractEntityModel {

    @Column(name = "day_of_week", nullable = false)
    private DayOfWeek dayOfWeek;

    @Column(name = "start", nullable = false)
    private int start;

    @Column(name = "end", nullable = false)
    private int end;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "venue_id")
    private Venue venue;

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }
}
