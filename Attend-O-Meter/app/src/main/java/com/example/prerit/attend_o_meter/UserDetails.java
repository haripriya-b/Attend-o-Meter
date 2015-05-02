package com.example.prerit.attend_o_meter;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by prerit on 1/4/15.
 */
@ParseClassName("UserDetails")
public class UserDetails extends ParseObject {
    public UserDetails() {
    }

    public String getEmailId() {
        return getString("emailId");
    }
    public String getPassword() {
        return getString("password");
    }
    public String getFirstName() {
        return getString("firstName");

    }
    public String getLastName() {
        return getString("lastName");
    }
    public String getUserType() {
        return getString("userType");
    }
    public void setEmailId(String emailId){
        put("emailId",emailId);
    }
    public void setPassword(String password){
        put("password",password);
    }
    public void setFirstName(String fname){
        put("firstName",fname);
    }
    public void setLastName(String lname){
        put("lastName",lname);
    }
}
