package com.example.prerit.attend_o_meter;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.Date;

/**
 * Created by haripriya on 10/4/15.
 */


    @ParseClassName("Semester")
    public class Semester extends ParseObject {
        public Semester() {
        }

        public String getSemesterName(){
                return getString("semesterName");
        }

        public Date getStartDate() {
            return getDate("startDate");
        }

        public Date getEndDate() {
        return getDate("endDate");
        }

        public void setSemesterName(String semName){
            put("semesterName",semName);
        }

        public void setStartDate(Date startDate) {
            put("startDate",startDate);
        }

        public void setEndDate(Date endDate) {
            put("endDate",endDate);
        }
}

