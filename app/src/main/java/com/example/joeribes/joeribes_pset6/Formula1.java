package com.example.joeribes.joeribes_pset6;

import java.io.Serializable;

public class Formula1 implements Serializable{
    public String name;
    public String year;

    // Default constructor
    public Formula1() {}

    public Formula1(String name, String year) {
        this.name = name;
        this.year = year;
    }


}
