package com.waiter.server.api.venue.model.response;

import com.waiter.server.services.venue.model.Schedule;

import java.time.DayOfWeek;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author shahenpoghosyan
 */
public class ScheduleClientModel {

    private DayOfWeek dayOfWeek;
    private int start;
    private int end;

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

    public static ScheduleClientModel convert(Schedule schedule) {
        ScheduleClientModel clientModel = new ScheduleClientModel();
        clientModel.setEnd(schedule.getEnd());
        clientModel.setStart(schedule.getStart());
        clientModel.setDayOfWeek(schedule.getDayOfWeek());
        return clientModel;
    }

    public static List<ScheduleClientModel> convert(List<Schedule> schedules) {
        return schedules.stream().map(ScheduleClientModel::convert).collect(Collectors.toList());
    }
}
