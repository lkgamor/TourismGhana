package com.example.louis.tourismghana.Adapters;

/**
 * Created by Louis on 3/30/2018.
 */

public class Symbols {

    private String name, meaning, literal, image;

    public Symbols()
    {

    }

    public Symbols(String name, String literal, String meaning, String image) {
        this.name = name;
        this.literal = literal;
        this.meaning = meaning;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLiteral() {
        return literal;
    }

    public void setLiteral(String literal) {
        this.literal = literal;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
