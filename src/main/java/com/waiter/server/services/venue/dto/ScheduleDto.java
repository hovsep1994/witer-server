package com.waiter.server.services.venue.dto;

import java.time.DayOfWeek;

/**
 * @author shahenpoghosyan
 */
public class ScheduleDto {

    private int start;
    private int end;
    private DayOfWeek dayOfWeek;


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

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
}
