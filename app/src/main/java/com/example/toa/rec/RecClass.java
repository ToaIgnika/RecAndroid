package com.example.toa.rec;

import org.json.JSONObject;

public class RecClass {
    String classID;
    String className;

    public RecClass(){};

    public RecClass(String classID, String className) {
        this.classID = classID;
        this.className = className;
    }

    public RecClass fromJson(JSONObject j) {
        try {
            this.classID = j.getString("classID");
            this.className = j.getString("className");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public String getClassID() {
        return classID;
    }

    public void setClassID(String classID) {
        this.classID = classID;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public String toString() {
        return className;
    }
}
