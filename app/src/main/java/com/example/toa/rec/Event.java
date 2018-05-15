package com.example.toa.rec;

import org.json.JSONObject;

import java.util.Comparator;

public class Event implements Comparator<Event> {



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
            this.beginMin = j.getString("beginMin");
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

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getEventDay() {
        return eventDay;
    }

    public void setEventDay(String eventDay) {
        this.eventDay = eventDay;
    }

    public String getUsedSlots() {
        return usedSlots;
    }

    public void setUsedSlots(String usedSlots) {
        this.usedSlots = usedSlots;
    }

    public String getMaxSlots() {
        return maxSlots;
    }

    public void setMaxSlots(String maxSlots) {
        this.maxSlots = maxSlots;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassLocation() {
        return classLocation;
    }

    public void setClassLocation(String classLocation) {
        this.classLocation = classLocation;
    }

    public String getInstructorID() {
        return instructorID;
    }

    public void setInstructorID(String instructorID) {
        this.instructorID = instructorID;
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

    public String getEndHour() {
        return endHour;
    }

    public void setEndHour(String endHour) {
        this.endHour = endHour;
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

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getHexColor() {
        return hexColor;
    }

    public void setHexColor(String hexColor) {
        this.hexColor = hexColor;
    }

    @Override
    public String
    toString() {
        return "Event{" +
                "eventID='" + eventID + '\'' +
                ", eventDay='" + eventDay + '\'' +
                ", usedSlots='" + usedSlots + '\'' +
                ", maxSlots='" + maxSlots + '\'' +
                ", active='" + active + '\'' +
                ", className='" + className + '\'' +
                ", classLocation='" + classLocation + '\'' +
                ", instructorID='" + instructorID + '\'' +
                ", beginHour='" + beginHour + '\'' +
                ", beginMin='" + beginMin + '\'' +
                ", endHour='" + endHour + '\'' +
                ", endMin='" + endMin + '\'' +
                ", dayOfWeek='" + dayOfWeek + '\'' +
                ", classDescription='" + classDescription + '\'' +
                ", classImageURL='" + classImageURL + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", photoURL='" + photoURL + '\'' +
                ", bio='" + bio + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", hexColor='" + hexColor + '\'' +
                '}';
    }

    @Override
    public int compare(Event o1, Event o2) {

        return 0;
    }
}
