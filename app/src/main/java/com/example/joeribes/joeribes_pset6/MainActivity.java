package com.example.joeribes.joeribes_pset6;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    String email;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = "joeriborn@gmail.com";
        // Password needs to be at least 6 characters
        password = "admin123";

        mAuth = FirebaseAuth.getInstance();
        setListener();
    }

    private void setListener() {
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
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

    public void createUser(View view) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("create user", "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(MainActivity.this, "Created user: " + email ,
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    public void overView(View view) {
        Intent intent = new Intent(getBaseContext(), OverviewActivity.class);
        startActivity(intent);
        finish();
    }

    public void logInUser(View view) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("sign in", "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w("email", "signInWithEmail:failed", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Logged in user: " + email ,
                                    Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(getBaseContext(), SecondActivity.class);
                            startActivity(intent);
                            finish();
                        }

                        // ...
                    }
                });
    }


    public void apiSearch(View view) {
        String webURL = "http://ergast.com/api/f1/current/last/results.json";
        DriverResultsAsyncTask asyncTask = new DriverResultsAsyncTask(this);
        asyncTask.execute(webURL);
    }

    public void startIntentDriverResults(DriverResults[] driverResults) {
        Intent driverResultsIntent = new Intent(this, DriverResultsActivity.class);
        driverResultsIntent.putExtra("driverResults", driverResults);
        this.startActivity(driverResultsIntent);
    }

    public void apiSearchRaceSchedule(View view) {
        String webURL = "http://ergast.com/api/f1/current.json";
        RaceScheduleAsyncTask asyncTask = new RaceScheduleAsyncTask(this);
        asyncTask.execute(webURL);
    }

    public void startIntentRaceSchedule(RaceSchedule[] raceSchedules) {
        Intent raceScheduleIntent = new Intent(this, RaceScheduleActivity.class);
        raceScheduleIntent.putExtra("raceSchedule", raceSchedules);
        this.startActivity(raceScheduleIntent);
    }


    public void apiSearchStandings(View view) {
        String webURL = "http://ergast.com/api/f1/current/driverStandings.json";
        StandingsAsyncTask asyncTask = new StandingsAsyncTask(this);
        asyncTask.execute(webURL);
    }

    public void startIntentStandings(Standings[] standings) {
        Intent standingsIntent = new Intent(this, StandingsActivity.class);
        standingsIntent.putExtra("standings", standings);
        this.startActivity(standingsIntent);
    }

    public void apiSearchDrivers(View view) {
        String webURL = "http://ergast.com/api/f1/current/drivers.json";
        DriverAsyncTask asyncTask = new DriverAsyncTask(this);
        asyncTask.execute(webURL);
    }

    public void startIntentDrivers(Driver[] driver) {
        Intent driversIntent = new Intent(this, DriverActivity.class);
        driversIntent.putExtra("drivers", driver);
        this.startActivity(driversIntent);
    }




}
