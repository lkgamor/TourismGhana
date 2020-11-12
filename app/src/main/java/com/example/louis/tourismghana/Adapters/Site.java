package com.example.louis.tourismghana.Adapters;

/**
 * Created by Louis on 4/2/2018.
 */

public class Site {

    private String name;
    private String image;
    private String description;
    private String location;
    private String number;
    private String latitude, longitude;

    public Site(){

    }

    public Site(String name, String image, String description, String location, String number, String latitude, String longitude) {
        this.name = name;
        this.image = image;
        this.description = description;
        this.location = location;
        this.number = number;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
