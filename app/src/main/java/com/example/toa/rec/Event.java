package com.example.toa.rec;

import org.json.JSONObject;

public class Event {



    private String eventID, eventDay, usedSlots, maxSlots, active, className, classLocation,
    instructorID, beginHour, beginMin, endHour, endMin, dayOfWeek, classDescription,
    classImageURL, firstname, lastname, photoURL, bio, categoryName, hexColor;


    public Event() {
    }

    public Event fromJson(JSONObject j) {
        try {
            this.eventID = j.getString("eventID");
            this.eventDay = j.getString("eventDay");
            this.usedSlots = j.getString("usedSlots");
            this.maxSlots = j.getString("maxSlots");
            this.active = j.getString("active");
            this.className = j.getString("className");
            this.classLocation = j.getString("classLocation");
            this.instructorID = j.getString("instructorID");
            this.beginHour = j.getString("beginHour");
            this.beginMin = j.getString(" beginMin");
            this.endHour = j.getString("endHour");
            this.endMin = j.getString("endMin");
            this.dayOfWeek = j.getString("dayOfWeek");
            this.classDescription = j.getString("classDescription");
            this.classImageURL = j.getString("classImageURL");
            this.firstname = j.getString("firstname");
            this.lastname = j.getString("lastname");
            this.photoURL = j.getString("photoURL");
            this.bio = j.getString("bio");
            this.categoryName = j.getString("categoryName");
            this.hexColor = j.getString("hexColor");



        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

}
