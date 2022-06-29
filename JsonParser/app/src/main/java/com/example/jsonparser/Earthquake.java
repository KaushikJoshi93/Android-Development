package com.example.jsonparser;

public class Earthquake {
    private String magnitude , location , time;

    //    Constructor method for Earthquake class
    public Earthquake(String magnitude, String time, String location) {
        this.magnitude = magnitude;
        this.time = time;
        this.location = location;
    }




    public String getMagnitude() {
        return magnitude;
    }

    public String getTime() {
        return time;
    }

    public String getLocation() {
        return location;
    }
}
