package com.example.louis.tourismghana.Adapters;

/**
 * Created by Louis on 4/6/2018.
 */

public class Hotels {

    private String name;
    private String rating;
    private String price;
    private String number;
    private String location;
    private String image;

    public Hotels() {
    }

    public Hotels(String name, String rating, String price, String number, String location, String image) {
        this.name = name;
        this.rating = rating;
        this.price = price;
        this.number = number;
        this.location = location;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
