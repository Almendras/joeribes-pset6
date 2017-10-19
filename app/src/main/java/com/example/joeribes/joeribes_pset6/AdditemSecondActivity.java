package com.example.joeribes.joeribes_pset6;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class AdditemSecondActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase;
    private FirebaseUser user;
    String group;
    String season;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additem_second);

        mAuth = FirebaseAuth.getInstance();
        setListener();

        // Initialize database reference
        mDatabase = FirebaseDatabase.getInstance().getReference();

        Intent intent = getIntent();
        String test = intent.getStringExtra("index");

        switch (test) {
            case "1":
                DriverResults[] driverResultsArray = (DriverResults[]) this.getIntent().getSerializableExtra("driverResultsAdd");
                ArrayList<DriverResults> driversResults = new ArrayList<>(Arrays.asList(driverResultsArray));
                season = driversResults.get(0).getSeason();
                group = "Race Results";

                break;
            case "2":
                RaceSchedule[] raceSchedulesArray = (RaceSchedule[]) this.getIntent().getSerializableExtra("raceScheduleAdd");
                ArrayList<RaceSchedule> raceSchedules = new ArrayList<>(Arrays.asList(raceSchedulesArray));
                season = raceSchedules.get(0).getSeason();
                group = "Race Schedule";
                break;
            case "3":
                Standings[] standingsArray = (Standings[]) this.getIntent().getSerializableExtra("standingsAdd");
                ArrayList<Standings> drivers = new ArrayList<>(Arrays.asList(standingsArray));
                season = drivers.get(0).getSeason();
                group = "Driver Standings";
                break;
        }

        addToDatabase();
    }

    public void addToDatabase() {
        Customization customItem = new Customization(group, season);
        mDatabase.child(mAuth.getCurrentUser().getUid()).child("Driver Standings " + season).setValue(customItem);

        Intent deleteItemIntent = new Intent(getBaseContext(), OverviewActivity.class);
        startActivity(deleteItemIntent);
        finish();
    }

    private void setListener() {
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("signed in", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d("signed out", "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
    }



    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
