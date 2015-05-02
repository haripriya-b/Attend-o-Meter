package com.example.prerit.attend_o_meter;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by haripriya on 17/4/15.
 */

@ParseClassName("CourseSchedule")
public class CourseSchedule extends ParseObject {
    public CourseSchedule() {
    }

    public String getCourseId() { return getString("courseId");}

    public String getDayOfWeek() { return getString("dayOfWeek");}

    public String getTimeHrs() {return  getString("timeHrs");}

    public String getTimeMins() {return getString("timeMins");}

    public String getAM_PM() {return getString("AM_PM");}

    public void setCourseId(String id) {put("courseId", id);}

    public void setDayOfWeek(String day) {put("dayOfWeek",day);}

    public void setTimeHrs(int hrs) {put("timeHrs",hrs);}

    public void setTimeMins(int mins) {put("timeMins",mins);}

    public void setAM_PM(String am_pm) {put("AM_PM",am_pm);}

}

