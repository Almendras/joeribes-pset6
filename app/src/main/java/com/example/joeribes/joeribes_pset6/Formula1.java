package com.example.joeribes.joeribes_pset6;

import java.io.Serializable;

/**
 * Created by Joeri Bes on 16-10-2017.
 */

public class Formula1 implements Serializable{
    public String name;
    public String year;

    // Default constrcuctor
    public Formula1() {}

    public Formula1(String name, String year) {
        this.name = name;
        this.year = year;
    }


}
