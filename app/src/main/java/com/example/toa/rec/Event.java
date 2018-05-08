package com.example.toa.rec;

import org.json.JSONObject;

public class Event {

    private int timeSlot;
    private String eventName;
    private String eventDescription;

    public Event() {}

    public Event (int timeSlot, String name, String description) {
        this.timeSlot = timeSlot;
        this.eventName = name;
        this.eventDescription = description;
    }


    public int getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(int timeSlot) {
        this.timeSlot = timeSlot;
    }

    public String getName() {
        return eventName;
    }

    public void setName(String name) {
        this.eventName = name;
    }

    public String getDescription() {
        return eventDescription;
    }

    public void setDescription(String description) {
        this.eventDescription = description;
    }

    public Event fromJson(JSONObject j) {
        try {
            this.timeSlot = j.getInt("timeSlot");
            this.eventName = j.getString("eventName");
            this.eventDescription = j.getString("eventDescription");
        } catch (Exception e) {

        }
        return this;
    }

}
