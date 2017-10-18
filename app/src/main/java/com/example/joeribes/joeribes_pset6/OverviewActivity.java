package com.example.joeribes.joeribes_pset6;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OverviewActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    // Initialize values
    ListView overviewListView;
    //ArrayList testArray;
    ArrayList<Customization> customItems;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase;
    private FirebaseUser user;
    NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        mAuth = FirebaseAuth.getInstance();
        setListener();

        // Initialize database reference
        mDatabase = FirebaseDatabase.getInstance().getReference();
        customItems = new ArrayList<>();

        getCustoms();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



    }

    public void getCustoms() {
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Retrieve the object from the database
                DataSnapshot userSnapShot = dataSnapshot.child(user.getUid());
                Iterable<DataSnapshot> userChildren = userSnapShot.getChildren();

                for(DataSnapshot item: userChildren) {
                    Customization c = item.getValue(Customization.class);
                    customItems.add(c);
                }

                View headerView = navigationView.inflateHeaderView(R.layout.nav_header_overview);
                TextView usernameOver = headerView.findViewById(R.id.usernameOverview);
                usernameOver.setText(user.getEmail());
                showAdapter();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting the data failed, log a message
                Log.w("Cancel", "Something went wrong: ", databaseError.toException());
            }
        };

        mDatabase.addValueEventListener(postListener);

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


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.overview, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent addItemIntent = new Intent(getBaseContext(), AddItemActivity.class);
            startActivity(addItemIntent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {

        } else if (id == R.id.nav_profile_list) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_logout) {
            FirebaseAuth.getInstance().signOut();

            Intent mainIntent = new Intent(getBaseContext(), MainActivity.class);
            startActivity(mainIntent);
            finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // Show the listview of the overview
    public void showAdapter() {
        ListAdapter myAdapter = new OverviewAdapter(this, customItems);
        overviewListView = (ListView) findViewById(R.id.overviewListView);
        assert overviewListView != null;

        overviewListView.setAdapter(myAdapter);

        overviewListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Customization item = customItems.get(position);
                        String name = item.getItem();
                        String season = item.getSeason();
                        String webURL;
                        switch(name) {
                            case "Driver Standings":
                                webURL = "http://ergast.com/api/f1/" + season + "/driverStandings.json";
                                StandingsAsyncTask asyncTaskStanding = new StandingsAsyncTask(OverviewActivity.this);
                                asyncTaskStanding.execute(webURL);
                                break;
                            case "Driver Information":
                                webURL = "http://ergast.com/api/f1/" + season + "/drivers.json";
                                DriverAsyncTask asyncTaskDriver = new DriverAsyncTask(OverviewActivity.this);
                                asyncTaskDriver.execute(webURL);
                                break;
                            case "Race Results":
                                webURL = "http://ergast.com/api/f1/" + season + "/1/results.json";
                                DriverResultsAsyncTask asyncTaskDriverResults= new DriverResultsAsyncTask(OverviewActivity.this);
                                asyncTaskDriverResults.execute(webURL);
                                break;
                            case "Race Schedule":
                                webURL = "http://ergast.com/api/f1/" + season + ".json";
                                RaceScheduleAsyncTask asyncTaskRaceSchedule = new RaceScheduleAsyncTask(OverviewActivity.this);
                                asyncTaskRaceSchedule.execute(webURL);
                                break;
                        }
                    }
                }
        );
    }

    public void startIntentDriverResults(DriverResults[] driverResults) {
        Intent driverResultsIntent = new Intent(this, DriverResultsActivity.class);
        driverResultsIntent.putExtra("driverResults", driverResults);
        this.startActivity(driverResultsIntent);
    }

    public void startIntentRaceSchedule(RaceSchedule[] raceSchedules) {
        Intent raceScheduleIntent = new Intent(this, RaceScheduleActivity.class);
        raceScheduleIntent.putExtra("raceSchedule", raceSchedules);
        this.startActivity(raceScheduleIntent);
    }

    public void startIntentStandings(Standings[] standings) {
        Intent standingsIntent = new Intent(this, StandingsActivity.class);
        standingsIntent.putExtra("standings", standings);
        this.startActivity(standingsIntent);
    }

    public void startIntentDrivers(Driver[] driver) {
        Intent driversIntent = new Intent(this, DriverActivity.class);
        driversIntent.putExtra("drivers", driver);
        this.startActivity(driversIntent);
    }
}
