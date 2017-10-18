package com.example.joeribes.joeribes_pset6;

import java.io.Serializable;

public class RaceSchedule implements Serializable{
    private String season, round, raceName, time, date;

    // Default constructor
    public RaceSchedule() {}

    public RaceSchedule(String season, String round, String raceName, String time, String date) {
        this.season = season;
        this.round = round;
        this.raceName = raceName;
        this.time = time;
        this.date = date;
    }

    // Returns the season
    public String getSeason() {
        return season;
    }

    // Returns the round
    public String getRound() {
        return round;
    }

    // Returns the race Track Circuit
    public String getRaceName() {
        return raceName;
    }

    // Returns the scheduled time in local time zone
    public String getTime(){
        return time;
    }

    // Returns the date
    public String getDate() {
        return date;
    }


}
