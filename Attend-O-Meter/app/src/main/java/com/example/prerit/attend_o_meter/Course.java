package com.example.prerit.attend_o_meter;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.Date;

/**
 * Created by haripriya on 13/4/15.
 */
    @ParseClassName("Course")
    public class Course extends ParseObject {
        public Course() {
        }

        public String getCourseId() { return getString("courseId");}

        public String getCourseName() { return getString("courseName");}

        public String getSemester() { return getString("semester");}

        public String getProfessor() { return getString("professor");}

        public int getTotalLectures() { return getInt("totalLectures");}

        public int getCompletedLecture() { return getInt("completedLectures");}

        public String getMinAttendance() { return getString("minAttendance");}

        public void setCourseId(String id) {put("courseId", id);}

        public void setCourseName(String cname) {put("courseName", cname);}

        public void setSemester(String sem) {put("semester", sem);}

        public void setProfessor(String prof) {put("professor", prof);}

        public void setTotalLectures(int tot) {put("totalLectures",tot);}

        public void setCompletedLecture(int comp) {put("completedLectures",comp);}

        public void setMinAttendance(String attdn) {put("minAttendance",attdn);}

    }

