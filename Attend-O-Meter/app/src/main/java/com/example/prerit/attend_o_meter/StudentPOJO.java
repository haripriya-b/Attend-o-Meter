package com.example.prerit.attend_o_meter;

/**
 * Created by haripriya on 22/4/15.
 */
public class StudentPOJO {

    private String objectID = "";
    private String studentID = "";
    private String studentName = "";
    private boolean attended = false;

    public String getObjectID() {
        return objectID;
    }

    public void setObjectID(String objectID) {
        this.objectID = objectID;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public boolean isAttended() {
        return attended;
    }

    public void setAttended(boolean attended) {
        this.attended = attended;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }
}
