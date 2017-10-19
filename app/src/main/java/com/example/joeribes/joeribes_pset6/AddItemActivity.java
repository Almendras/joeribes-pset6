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
    String name, selectedSeason, webURL;
    ArrayList<String> items, season;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        initializeValueSpinnerItem();
        createSpinnerItems(items);

        initializeValueSpinnerSeason();
        createSpinnerSeason(season);

    }

    // Creates a spinner with Items
    public void createSpinnerItems(ArrayList<String> items) {
        final Spinner spinItem = (Spinner) findViewById(R.id.item_spinner_select);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, items);
        spinItem.setAdapter(adapter);

        spinItem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                name = spinItem.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });
    }

    public void createSpinnerSeason(ArrayList<String> season) {
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
    }


    // Creates a spinner for the items
    public void initializeValueSpinnerItem() {
        items = new ArrayList<>();
        items.add("Driver Standings");
        items.add("Race Results");
        items.add("Race Schedule");
    }

    // This will create a spinnner values from 1950 to the current year
    public void initializeValueSpinnerSeason() {
        season = new ArrayList<>();
        int currentSeason = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 1950; i <= currentSeason; i++) {
            season.add(Integer.toString(i));
        }
    }





    public void searchApi(View view) {
        switch(name) {
            case "Driver Standings":
                driverStandingsSearch(selectedSeason);
                break;
            case "Driver Information":
                driverInformationSearch(selectedSeason);
                break;
            case "Race Results":
                raceResultsSearch(selectedSeason);

                break;
            case "Race Schedule":
                raceScheduleSearch(selectedSeason);
                break;
        }
    }

    // Searches in the API for driver Standings
    public void driverStandingsSearch(String selectedSeason) {
        webURL = "http://ergast.com/api/f1/" + selectedSeason + "/driverStandings.json";
        StandingsAsyncTaskAdd asyncTaskStanding = new StandingsAsyncTaskAdd(AddItemActivity.this);
        asyncTaskStanding.execute(webURL);
    }

    // Searches in the API for driver Information
    public void driverInformationSearch(String selectedSeason) {
        webURL = "http://ergast.com/api/f1/" + selectedSeason + "/drivers.json";
        DriverAsyncTaskAdd asyncTaskDriver = new DriverAsyncTaskAdd(AddItemActivity.this);
        asyncTaskDriver.execute(webURL);
    }

    // Searches in the API for Race Results
    public void raceResultsSearch(String selectedSeason) {
        webURL = "http://ergast.com/api/f1/" + selectedSeason + "/1/results.json";
        DriverResultsAsyncTaskAdd asyncTaskDriverResults= new DriverResultsAsyncTaskAdd(AddItemActivity.this);
        asyncTaskDriverResults.execute(webURL);
    }

    // Searches in the API for the Race Schedule
    public void raceScheduleSearch(String selectedSeason) {
        webURL = "http://ergast.com/api/f1/" + selectedSeason + ".json";
        RaceScheduleAsyncTaskAdd asyncTaskRaceSchedule = new RaceScheduleAsyncTaskAdd(AddItemActivity.this);
        asyncTaskRaceSchedule.execute(webURL);
    }

    // Starts the driverResults intent for the second Activity
    public void startIntentDriverResults(DriverResults[] driverResults) {
        Intent driverResultsIntent = new Intent(this, AdditemSecondActivity.class);
        driverResultsIntent.putExtra("driverResultsAdd", driverResults);
        driverResultsIntent.putExtra("index", "1");
        this.startActivity(driverResultsIntent);
    }

    // Starts the raceSchedule intent for the second Activity
    public void startIntentRaceSchedule(RaceSchedule[] raceSchedules) {
        Intent raceScheduleIntent = new Intent(this, AdditemSecondActivity.class);
        raceScheduleIntent.putExtra("raceScheduleAdd", raceSchedules);
        raceScheduleIntent.putExtra("index", "2");
        this.startActivity(raceScheduleIntent);
    }

    // Starts the Standings intent for the second Activity
    public void startIntentStandings(Standings[] standings) {
        Intent standingsIntent = new Intent(this, AdditemSecondActivity.class);
        standingsIntent.putExtra("standingsAdd", standings);
        standingsIntent.putExtra("index", "3");
        this.startActivity(standingsIntent);
    }

    // Starts the driversInformation intent for the second Activity
    public void startIntentDrivers(Driver[] driver) {
        Intent driversIntent = new Intent(this, AdditemSecondActivity.class);
        driversIntent.putExtra("driversAdd", driver);
        driversIntent.putExtra("index", "4");
        this.startActivity(driversIntent);
    }


}
