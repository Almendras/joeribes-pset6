package com.example.joeribes.joeribes_pset6;


import java.io.Serializable;

public class DriverResults implements Serializable {
    private String raceName, season, driver, position, constructor, time, status;

    // Default constructor
    public DriverResults() {}

    public DriverResults(String raceName, String season, String driver, String position, String constructor, String time, String status) {
        this.raceName = raceName;
        this.season = season;
        this.driver = driver;
        this.position = position;
        this.constructor = constructor;
        this.time = time;
        this.status = status;
    }

    // Returns the Grand Prix name
    public String getRaceName() {
        return raceName;
    }

    // Returns the season
    public String getSeason() {
        return season;
    }

    // Returns the driver name
    public String getDriver() {
        return driver;
    }

    // Returns the position
    public String getPosition() {
        return position;
    }

    // Returns the constructor
    public String getConstructor() {
        return constructor;
    }

    // Returns the race time
    public String getTime() {
        return time;
    }

    // Returns the status of the driver
    public String getStatus() {
        return status;
    }
}

