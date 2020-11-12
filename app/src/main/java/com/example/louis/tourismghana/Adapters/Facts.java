package com.example.louis.tourismghana.Adapters;

/**
 * Created by Louis on 3/18/2018.
 */
public class Facts {

    String title,desc;

    public Facts()
    {

    }

    public Facts(String title, String desc)
    {
        this.title = title;
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

