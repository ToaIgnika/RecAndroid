package com.example.toa.rec;

import org.json.JSONObject;

public class Event {

    private int eventID;
    private String eventDate;
    private String eventName;
    private String eventDescription;
    private int slots;

    public Event() {}

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public void setSlots(int slots) {
        this.slots = slots;
    }

    public int getEventID() {

        return eventID;
    }

    public String getEventDate() {
        return eventDate;
    }

    public String getEventName() {
        return eventName;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public int getSlots() {
        return slots;
    }

    public Event(int eventID, String eventDate, String eventName, String eventDescription, int slots) {

        this.eventID = eventID;
        this.eventDate = eventDate;
        this.eventName = eventName;
        this.eventDescription = eventDescription;
        this.slots = slots;
    }

    public Event fromJson(JSONObject j) {
        try {


            this.eventID = j.getInt("eventID");
            this.eventDate = j.getString("eventDate");
            this.eventName = j.getString("eventName");
            this.eventDescription = j.getString("eventDescription");
            this.slots = j.getInt("slots");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

}
