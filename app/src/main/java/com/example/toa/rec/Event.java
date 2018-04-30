package com.example.toa.rec;

import org.json.JSONObject;

public class Event {
    private String name;
    private String description;

    public Event() {}

    public Event (String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Event fromJson(JSONObject j) {
        try {
            this.name = j.getString("name");
            this.description = j.getString("description");
        } catch (Exception e) {

        }
        return this;
    }

}
