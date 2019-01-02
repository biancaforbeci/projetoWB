package com.example.biaeweverton.projetowb.files.Models;

import java.io.Serializable;
import java.util.Date;

public class  Log implements Serializable {
    private String description;
    private Date date;
    private String userID;
    private String methodName;

    public Log(String methodName, Date date, String description, String userID){
        this.date = date;
        this.description = description;
        this.userID = userID;
        this.methodName = methodName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getDescription() {
        return description;

    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
