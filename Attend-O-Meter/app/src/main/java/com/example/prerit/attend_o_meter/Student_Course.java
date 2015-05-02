package com.example.prerit.attend_o_meter;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by haripriya on 17/4/15.
 */
@ParseClassName("Student_Course")
public class Student_Course extends ParseObject {
    public Student_Course() {
    }

    public String getStudentId() {return getString("studentId");}
    public int getAttendance(){return getInt("attendance");}
    public String getCourseId() { return getString("courseId");}
    public void setStudentId(String studentId) {put("studentId", studentId);}

    public void setAttendance(int attendance) {put("attendance", attendance);}

    public void setCourseId(String id) {put("courseId", id);}

}

