package com.example.louis.tourismghana.Adapters;

/**
 * Created by Louis on 4/12/2018.
 */

public class Reviews {

    private String name;
    private String message;
    private String dateTime;

    public Reviews() {
    }

    public Reviews(String name, String message, String dateTime) {
        this.name = name;
        this.message = message;
        this.dateTime = dateTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
