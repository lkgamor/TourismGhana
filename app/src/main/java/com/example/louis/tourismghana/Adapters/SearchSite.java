package com.example.louis.tourismghana.Adapters;

/**
 * Created by Debby on 5/9/2018.
 */

public class SearchSite {

    private String name;
    private String image;

    public SearchSite(String image, String name) {
        this.image = image;
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
