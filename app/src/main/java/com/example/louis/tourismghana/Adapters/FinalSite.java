package com.example.louis.tourismghana.Adapters;

/**
 * Created by Louis on 4/4/2018.
 */

public class FinalSite {
    private String site_name;
    private String site_desc;
    private String site_contact;
    private String site_location;
    private String site_category;
    private String site_mapLoc;
    private String site_region;

    public FinalSite(){

    }

    public FinalSite(String site_name, String site_desc, String site_contact, String site_location, String site_category, String site_mapLoc, String site_region) {
        this.site_name = site_name;
        this.site_desc = site_desc;
        this.site_contact = site_contact;
        this.site_location = site_location;
        this.site_category = site_category;
        this.site_mapLoc = site_mapLoc;
        this.site_region = site_region;
    }

    public String getSite_name() {
        return site_name;
    }

    public void setSite_name(String site_name) {
        this.site_name = site_name;
    }

    public String getSite_desc() {
        return site_desc;
    }

    public void setSite_desc(String site_desc) {
        this.site_desc = site_desc;
    }

    public String getSite_contact() {
        return site_contact;
    }

    public void setSite_contact(String site_contact) {
        this.site_contact = site_contact;
    }

    public String getSite_location() {
        return site_location;
    }

    public void setSite_location(String site_location) {
        this.site_location = site_location;
    }

    public String getSite_category() {
        return site_category;
    }

    public void setSite_category(String site_category) {
        this.site_category = site_category;
    }

    public String getSite_mapLoc() {
        return site_mapLoc;
    }

    public void setSite_mapLoc(String site_mapLoc) {
        this.site_mapLoc = site_mapLoc;
    }

    public String getSite_region() {
        return site_region;
    }

    public void setSite_region(String site_region) {
        this.site_region = site_region;
    }
}
