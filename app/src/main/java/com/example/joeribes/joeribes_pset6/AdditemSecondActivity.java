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
    //Standings[] standingsArray;
    DriverResults[] driverResultsArray;
    Driver[] driverArray;
    RaceSchedule[] scheduleArray;
    String season;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additem_second);

        mAuth = FirebaseAuth.getInstance();
        setListener();

        // Initialize database reference
        mDatabase = FirebaseDatabase.getInstance().getReference();


        //Bundle bundle = this.getIntent().getExtras();
        /*
        String driverResults = bundle.getString("driverResults");
        String raceSchedule = bundle.getString("raceSchedule");
        String standings = bundle.getString("standings");
        String drivers = bundle.getString("drivers");
        int a = 5;
        */


        Intent intent = getIntent();
        Standings[] standingsArray = (Standings[]) this.getIntent().getSerializableExtra("standingsAdd");
        ArrayList<Standings> drivers = new ArrayList<>(Arrays.asList(standingsArray));
        season = drivers.get(0).getSeason();
        int a = 5;
        /*
        Intent intent = getIntent();
        if(intent.getExtras().containsKey("standingsAdd")) {
            Standings[] standingsArray = (Standings[]) intent.getSerializableExtra("standingsAdd");
            int a = 5;
        }
        */

        /*
            else if (intent.hasExtra("driverResults")) {
            driverResultsArray = (DriverResults[]) this.getIntent().getSerializableExtra("driverResults");
        } else if (intent.hasExtra("raceSchedule")) {
            scheduleArray = (RaceSchedule[]) this.getIntent().getSerializableExtra("raceSchedule");
        } else if (intent.hasExtra("drivers")) {
            driverArray = (Driver[]) this.getIntent().getSerializableExtra("drivers");
        }
        */

        addToDatabase();
    }

    public void addToDatabase() {
        Customization customItem = new Customization("Driver Standings", season);
        mDatabase.child(mAuth.getCurrentUser().getUid()).child("Driver Standings " + season).setValue(customItem);
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
