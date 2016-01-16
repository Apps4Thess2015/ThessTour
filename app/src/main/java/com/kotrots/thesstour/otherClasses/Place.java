package com.kotrots.thesstour.otherClasses;

/**
 * Created by Kostas on 23/12/2015.
 */
public class Place {

    private String id;
    private String name;
    private String langitude;
    private String longitude;
    private String info;
    private String time;
    private String details;

    public Place() {
    }
    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }

    public String getLangitude() {
        return langitude;
    }

    public void setLangitude(String langitude) {
        this.langitude = langitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getInfo() {return info;}

    public void setInfo(String info) { this.info = info; }

    public String getTime() { return time; }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
