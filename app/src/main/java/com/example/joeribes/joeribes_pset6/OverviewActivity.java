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
    ArrayList<Customization> customItems;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase;
    private FirebaseUser user;
    NavigationView navigationView;
    String season;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        createNavigationDrawer(toolbar);

        // Database connection
        mAuth = FirebaseAuth.getInstance();
        setListener();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Initialize the navigation view
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getCustoms();
    }

    // This will create the navigation drawer
    public void createNavigationDrawer(Toolbar toolbar) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

    }

    // This will retrieve the customization items from the database
    public void getCustoms() {
        customItems = new ArrayList<>();

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Retrieve the object from the database
                snapShot(dataSnapshot, user.getUid());
                setUsernameNavigation(user.getEmail());
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

    // Makes a snapshot of the database and adds it to customItems
    public void snapShot(DataSnapshot dataSnapshot, String userUid) {
        DataSnapshot userSnapShot = dataSnapshot.child(userUid);
        Iterable<DataSnapshot> userChildren = userSnapShot.getChildren();

        for(DataSnapshot item: userChildren) {
            Customization c = item.getValue(Customization.class);
            customItems.add(c);
        }
    }

    // This will set the username in the navigationView
    public void setUsernameNavigation(String userEmail) {
        View headerView = navigationView.inflateHeaderView(R.layout.nav_header_overview);
        TextView usernameOver = headerView.findViewById(R.id.usernameOverview);
        usernameOver.setText(userEmail);

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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {
            addItemIntent();
            return true;
        } else if (id == R.id.action_delete) {
            deleteItemActivity();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // This will invoke the addItem Activity
    public void addItemIntent() {
        Intent addItemIntent = new Intent(getBaseContext(), AddItemActivity.class);
        startActivity(addItemIntent);
        finish();
    }

    // This will invoke the deleteItem Acitivity
    public void deleteItemActivity() {
        Intent deleteItemIntent = new Intent(getBaseContext(), DeleteItemActivity.class);
        deleteItemIntent.putExtra("deleteCustomItem", customItems);
        startActivity(deleteItemIntent);
        finish();
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

    public void navigationViewHelper() {
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ListAdapter myAdapter = new OverviewAdapter(this, customItems);
        overviewListView = (ListView) findViewById(R.id.overviewListView);
        assert overviewListView != null;

        overviewListView.setAdapter(myAdapter);

    }


    // Show the listview of the overview
    public void showAdapter() {
        navigationViewHelper();

        overviewListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Customization item = customItems.get(position);
                        String name = item.getItem();
                        season = item.getSeason();
                        switch(name) {
                            case "Driver Standings":
                                driverStandingsHelper(season);
                                break;
                            case "Driver Information":
                                driverInformationHelper(season);
                                break;
                            case "Race Results":
                                raceResultsHelper(season);
                                break;
                            case "Race Schedule":
                                raceResultsHelper(season);
                                break;
                        }
                    }
                }
        );
    }

    // Driver standings helper for AsyncTask
    public void driverStandingsHelper(String season) {
        String webURL = "http://ergast.com/api/f1/" + season + "/driverStandings.json";
        StandingsAsyncTask asyncTaskStanding = new StandingsAsyncTask(OverviewActivity.this);
        asyncTaskStanding.execute(webURL);
    }

    // Driver information helper for AsyncTask
    public void driverInformationHelper(String season) {
        String webURL = "http://ergast.com/api/f1/" + season + "/drivers.json";
        DriverAsyncTask asyncTaskDriver = new DriverAsyncTask(OverviewActivity.this);
        asyncTaskDriver.execute(webURL);
    }

    // Race results helper for AsyncTask
    public void raceResultsHelper(String season) {
        String webURL = "http://ergast.com/api/f1/" + season + "/last/results.json";
        DriverResultsAsyncTask asyncTaskDriverResults= new DriverResultsAsyncTask(OverviewActivity.this);
        asyncTaskDriverResults.execute(webURL);
    }

    // Race schedule helper for AsyncTask
    public void raceScheduleHelper(String season) {
        String webURL = "http://ergast.com/api/f1/" + season + ".json";
        RaceScheduleAsyncTask asyncTaskRaceSchedule = new RaceScheduleAsyncTask(OverviewActivity.this);
        asyncTaskRaceSchedule.execute(webURL);
    }


    // Starts the activity for driver results
    public void startIntentDriverResults(DriverResults[] driverResults) {
        Intent driverResultsIntent = new Intent(this, DriverResultsActivity.class);
        driverResultsIntent.putExtra("driverResults", driverResults);
        driverResultsIntent.putExtra("season", season);
        this.startActivity(driverResultsIntent);
    }

    // Starts the activity for the race schedule
    public void startIntentRaceSchedule(RaceSchedule[] raceSchedules) {
        Intent raceScheduleIntent = new Intent(this, RaceScheduleActivity.class);
        raceScheduleIntent.putExtra("raceSchedule", raceSchedules);
        raceScheduleIntent.putExtra("season", season);
        this.startActivity(raceScheduleIntent);
    }

    // Starts the activity for the race standings
    public void startIntentStandings(Standings[] standings) {
        Intent standingsIntent = new Intent(this, StandingsActivity.class);
        standingsIntent.putExtra("standings", standings);
        standingsIntent.putExtra("season", season);
        this.startActivity(standingsIntent);
    }

    // Starts the activity for the driver information
    public void startIntentDrivers(Driver[] driver) {
        Intent driversIntent = new Intent(this, DriverActivity.class);
        driversIntent.putExtra("drivers", driver);
        driversIntent.putExtra("season", season);
        this.startActivity(driversIntent);
    }
}
