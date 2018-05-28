package com.example.louis.tourismghana.Adapters;

/**
 * Created by Louis on 4/30/2018.
 */

public class Suggestions {

    private String username;
    private String email;
    private String site_name;
    private String location;
    private String description;
    private String siteImageUrl;

    public Suggestions() {

    }

    public Suggestions(String username, String email, String site_name, String location, String description, String siteImageUrl) {
        this.username = username;
        this.email = email;
        this.site_name = site_name;
        this.location = location;
        this.description = description;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSite_name() {
        return site_name;
    }

    public void setSite_name(String site_name) {
        this.site_name = site_name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSiteImageUrl() {
        return siteImageUrl;
    }

    public void setSiteImageUrl(String siteImageUrl) {
        this.siteImageUrl = siteImageUrl;
    }
}
