package com.example.joeribes.joeribes_pset6;

import java.io.Serializable;


public class Driver implements Serializable {
    private String name, driverCode, driverNumber, dateOfBirth, nationality, wikiurl;

    // Default constructor
    public Driver() {}

    public Driver(String name, String driverCode, String driverNumber, String dateOfBirth, String nationality, String wikiurl) {
        this.name = name;
        this.driverCode = driverCode;
        this.driverNumber = driverNumber;
        this.dateOfBirth = dateOfBirth;
        this.nationality = nationality;
        this.wikiurl = wikiurl;
    }

    // Retrieve the name of the driver
    public String getName() {
        return name;
    }

    // Retrieve the the drivercode such as ALO for alonso
    public String getDriverCode() {
        return driverCode;
    }

    // Retrieve the driver number
    public String getDriverNumber() {
        return driverNumber;
    }
    
    // Retrieve the date of birth of the driver
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    // Retrieve the nationality of the driver
    public String getNationality() {
        return nationality;
    }

    // Retrieve the wikipage url of the driver
    public String getWikiurl() {
        return wikiurl;
    }
}
