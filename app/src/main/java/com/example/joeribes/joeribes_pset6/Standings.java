package com.example.joeribes.joeribes_pset6;

import java.io.Serializable;

public class Standings implements Serializable {
    private String season, round, position, points, wins, driver, constructor;

    // Default constructor
    public Standings() {}

    public Standings(String season, String round, String position, String points, String wins, String driver, String constructor) {
        this.season = season;
        this.round = round;
        this.position = position;
        this.points = points;
        this.wins = wins;
        this.driver = driver;
        this.constructor = constructor;
    }

    // Returns the season
    public String getSeason() {
        return season;
    }

    // Returns the rounds
    public String getRound() {
        return round;
    }

    // Returns the position of the driver
    public String getPosition() {
        return position;
    }

    // Returns the amount of points
    public String getPoints() {
        return points;
    }

    // Returns the amount of wins
    public String getWins(){
        return wins;
    }

    // Returns the driver name
    public String getDriver() {
        return driver;
    }

    // Returns the constructor
    public String getConstructor() {
        return constructor;
    }



}
