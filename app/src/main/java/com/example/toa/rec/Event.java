package com.example.toa.rec;

import org.json.JSONObject;

public class Event {

    private String classID;
    private String className;
    private String instructorID;
    private String reservedSlots;
    private String availableSlots;
    private String beginDate;
    private String endDate;
    private String beginHour;
    private String beginMin;
    private String endMin;
    private String dayOfWeek;
    private String classDescription;
    private String classImageURL;

    public Event() {
    }

    public Event(String eventID, String className, String instructorID, String reservedSlots, String availableSlots, String beginDate, String endDate, String beginHour, String beginMin, String endMin, String dayOfWeek, String classDescription, String classImageURL) {

        this.classID = eventID;
        this.className = className;
        this.instructorID = instructorID;
        this.reservedSlots = reservedSlots;
        this.availableSlots = availableSlots;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.beginHour = beginHour;
        this.beginMin = beginMin;
        this.endMin = endMin;
        this.dayOfWeek = dayOfWeek;
        this.classDescription = classDescription;
        this.classImageURL = classImageURL;
    }

    public String getEventID() {

        return classID;
    }

    public void setEventID(String eventID) {
        this.classID = eventID;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getInstructorID() {
        return instructorID;
    }

    public void setInstructorID(String instructorID) {
        this.instructorID = instructorID;
    }

    public String getReservedSlots() {
        return reservedSlots;
    }

    public void setReservedSlots(String reservedSlots) {
        this.reservedSlots = reservedSlots;
    }

    public String getAvailableSlots() {
        return availableSlots;
    }

    public void setAvailableSlots(String availableSlots) {
        this.availableSlots = availableSlots;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getBeginHour() {
        return beginHour;
    }

    public void setBeginHour(String beginHour) {
        this.beginHour = beginHour;
    }

    public String getBeginMin() {
        return beginMin;
    }

    public void setBeginMin(String beginMin) {
        this.beginMin = beginMin;
    }

    public String getEndMin() {
        return endMin;
    }

    public void setEndMin(String endMin) {
        this.endMin = endMin;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getClassDescription() {
        return classDescription;
    }

    public void setClassDescription(String classDescription) {
        this.classDescription = classDescription;
    }

    public String getClassImageURL() {
        return classImageURL;
    }

    public void setClassImageURL(String classImageURL) {
        this.classImageURL = classImageURL;
    }

    public Event fromJson(JSONObject j) {
        try {

/*
            this.eventID = j.getInt("eventID");
            this.eventDate = j.getString("eventDate");
            this.eventName = j.getString("eventName");
            this.eventDescription = j.getString("eventDescription");
            this.slots = j.getInt("slots");
*/
            this.classID = j.getString("classID");
            this.className = j.getString("className");
            this.instructorID = j.getString("instructorID");
            this.reservedSlots = j.getString("reservedSlots");
            this.availableSlots = j.getString("availableSlots");
            this.beginDate = j.getString("beginDate");
            this.endDate = j.getString("endDate");
            this.beginHour = j.getString("beginHour");
            this.beginMin = j.getString("beginMin");
            this.endMin = j.getString("endMin");
            this.dayOfWeek = j.getString("dayOfWeek");
            this.classDescription = j.getString("classDescription");
            this.classImageURL =j.getString("classImageURL");


        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

}
