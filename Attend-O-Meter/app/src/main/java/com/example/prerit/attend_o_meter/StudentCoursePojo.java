package com.example.prerit.attend_o_meter;

/**
 * Created by prerit on 23/4/15.
 */
public class StudentCoursePojo {
    private String course_id= "";
    private String studentId = "";
    private int attendance = 0;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }



    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    //public String getCourse_name() {
     //   return course_name;
   // }

   // public void setCourse_name(String course_name) {
    //    this.course_name = course_name;
   // }

    public int getAttendance() {
        return attendance;
    }

    public void setAttendance(int attendance) {
        this.attendance = attendance;
    }
}
