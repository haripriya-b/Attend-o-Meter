package com.example.prerit.attend_o_meter;

import java.util.Date;

/**
 * Created by haripriya on 18/4/15.
 */
public class CoursePOJO {

    private String course_objectID = "";
    private String CourseID = "";
    private String CourseName = "";
    private Date time = null;
    private String min_attdn = "";
    private int totClasses = 0;
    private int compClasses = 0;
    private int attendance = 0;


    public String getCourse_objectID() {
        return course_objectID;
    }

    public void setCourse_objectID(String objectID) {
        course_objectID = objectID;
    }

    public int getAttendance() {return attendance;}

    public void setAttendance(int attendance) {this.attendance = attendance;}

    public int getTotClasses() { return totClasses; }

    public void setTotClasses(int totClasses) { this.totClasses = totClasses;}

    public int getCompClasses() {return compClasses;}

    public void setCompClasses(int compClasses) {this.compClasses = compClasses;}

    public String getCourseName() { return CourseName; }

    public void setCourseName(String courseName) { CourseName = courseName; }

    public String getCourseID() {
        return CourseID;
    }

    public void setCourseID(String courseID) {
        CourseID = courseID;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getMin_attdn() {
        return min_attdn;
    }

    public void setMin_attdn(String min_attdn) {
        this.min_attdn = min_attdn;
    }
}
