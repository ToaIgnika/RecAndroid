package com.example.toa.rec.ObjectModels;

import org.json.JSONObject;

/**
 * Defines an instructor object which retrieves an object from the Database
 */
public class Instructor {
    String firstName;
    String lastName;
    String instructorID;

    public Instructor(){};

    /**
     * Creates an instructor object from a JSON object
     *
     * @param j
     * @return
     */
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
