package com.example.joeribes.joeribes_pset6;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AdditemSecondActivity extends AppCompatActivity {
    Standings[] standingsArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additem_second);

        /*
        Bundle bundle = this.getIntent().getExtras();
        String driverResults = bundle.getString("driverResults");
        String raceSchedule = bundle.getString("raceSchedule");
        String standings = bundle.getString("standings");
        String drivers = bundle.getString("drivers");
        int a = 5;
        */
        /*

        Intent intent = getIntent();
        if(intent.hasExtra("standings")) {
            standingsArray = (Standings[]) this.getIntent().getSerializableExtra("standings");
            int a = 5;
        }
        */






    }
}
