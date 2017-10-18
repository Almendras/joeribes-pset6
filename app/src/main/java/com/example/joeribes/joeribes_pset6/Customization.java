package com.example.joeribes.joeribes_pset6;

import java.io.Serializable;

public class Customization implements Serializable{
    public String item, season;

    // Default constructor
    public Customization() {}

    public Customization(String item, String season) {
        this.item = item;
        this.season = season;
    }

    // Retrieve the item
    public String getItem() {
        return item;
    }

    // Retrieve the season
    public String getSeason() {
        return season;
    }


}
