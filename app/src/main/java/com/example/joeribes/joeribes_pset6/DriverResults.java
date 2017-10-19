package com.example.joeribes.joeribes_pset6;


import java.io.Serializable;

public class DriverResults implements Serializable {
    private String raceName, driver, position, time, status;

    // Default constructor
    public DriverResults() {}

    public DriverResults(String raceName, String driver, String position, String time, String status) {
        this.raceName = raceName;
        this.driver = driver;
        this.position = position;
        this.time = time;
        this.status = status;
    }

    // Returns the Grand Prix name
    public String getRaceName() {
        return raceName;
    }

    // Returns the driver name
    public String getDriver() {
        return driver;
    }

    // Returns the position
    public String getPosition() {
        return position;
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

