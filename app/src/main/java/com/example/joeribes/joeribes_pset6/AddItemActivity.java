package com.example.joeribes.joeribes_pset6;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Calendar;

public class AddItemActivity extends AppCompatActivity {
    String name;
    String selectedSeason;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        // Creates a spinner for the items
        ArrayList<String> item = new ArrayList<String>();
        item.add("Driver Standings");
        item.add("Driver Information");
        item.add("Race Results");
        item.add("Race Schedule");

        final Spinner spinItem = (Spinner) findViewById(R.id.item_spinner_select);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, item);
        spinItem.setAdapter(adapter);

        spinItem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                name = spinItem.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });


        // This will create a spinnner from 1950 to the current year
        ArrayList<String> season = new ArrayList<String>();
        int currentSeason = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 1950; i <= currentSeason; i++) {
            season.add(Integer.toString(i));
        }

        ArrayAdapter<String> seasonAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, season);
        final Spinner spinSeason= (Spinner) findViewById(R.id.season_spinner);
        spinSeason.setAdapter(seasonAdapter);


        spinSeason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedSeason = spinSeason.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });



        //name = spinItem.getSelectedItem().toString();
        //selectedSeason = spinSeason.getSelectedItem().toString();
    }


    public void searchApi(View view) {
        String webURL;
        switch(name) {
            case "Driver Standings":
                webURL = "http://ergast.com/api/f1/" + selectedSeason + "/driverStandings.json";
                StandingsAsyncTaskAdd asyncTaskStanding = new StandingsAsyncTaskAdd(AddItemActivity.this);
                asyncTaskStanding.execute(webURL);
                break;
            case "Driver Information":
                webURL = "http://ergast.com/api/f1/" + selectedSeason + "/drivers.json";
                DriverAsyncTaskAdd asyncTaskDriver = new DriverAsyncTaskAdd(AddItemActivity.this);
                asyncTaskDriver.execute(webURL);
                break;
            case "Race Results":
                webURL = "http://ergast.com/api/f1/" + selectedSeason + "/1/results.json";
                DriverResultsAsyncTaskAdd asyncTaskDriverResults= new DriverResultsAsyncTaskAdd(AddItemActivity.this);
                asyncTaskDriverResults.execute(webURL);
                break;
            case "Race Schedule":
                webURL = "http://ergast.com/api/f1/" + selectedSeason + ".json";
                RaceScheduleAsyncTaskAdd asyncTaskRaceSchedule = new RaceScheduleAsyncTaskAdd(AddItemActivity.this);
                asyncTaskRaceSchedule.execute(webURL);
                break;
        }
    }


    public void startIntentDriverResults(DriverResults[] driverResults) {
        Intent driverResultsIntent = new Intent(this, AdditemSecondActivity.class);
        driverResultsIntent.putExtra("driverResultsAdd", driverResults);
        this.startActivity(driverResultsIntent);
    }

    public void startIntentRaceSchedule(RaceSchedule[] raceSchedules) {
        Intent raceScheduleIntent = new Intent(this, AdditemSecondActivity.class);
        raceScheduleIntent.putExtra("raceScheduleAdd", raceSchedules);
        this.startActivity(raceScheduleIntent);
    }

    public void startIntentStandings(Standings[] standings) {
        Intent standingsIntent = new Intent(this, AdditemSecondActivity.class);
        standingsIntent.putExtra("standingsAdd", standings);
        this.startActivity(standingsIntent);
    }

    public void startIntentDrivers(Driver[] driver) {
        Intent driversIntent = new Intent(this, AdditemSecondActivity.class);
        driversIntent.putExtra("driversAdd", driver);
        this.startActivity(driversIntent);
    }


}
