package com.example.prerit.attend_o_meter;

import java.util.Date;

/**
 * Created by prerit on 19/4/15.
 */
public class SchedulePOJO {

    private String dayOfWeek = "";
    private Date timeOfCourse=null;

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Date getTimeOfCourse() {
        return timeOfCourse;
    }

    public void setTimeOfCourse(Date timeOfCourse) {
        this.timeOfCourse = timeOfCourse;
    }
}
