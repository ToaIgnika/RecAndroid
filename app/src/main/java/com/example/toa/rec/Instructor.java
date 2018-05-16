package com.example.toa.rec;

import org.json.JSONObject;

public class Instructor {
    String firstName;
    String lastName;
    String instructorID;

    public Instructor(){};

    public Instructor fromJson(JSONObject j) {
        try {
            this.firstName = j.getString("firstName");
            this.lastName = j.getString("lastName");
            this.instructorID = j.getString("instructorID");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getInstructorID() {
        return instructorID;
    }

    public void setInstructorID(String instructorID) {
        this.instructorID = instructorID;
    }

    @Override
    public String toString() {
        return "" + firstName + " " + lastName;
    }
}
