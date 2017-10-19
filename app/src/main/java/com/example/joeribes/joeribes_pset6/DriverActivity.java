package com.example.joeribes.joeribes_pset6;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class DriverActivity extends AppCompatActivity {

    ListView driverView;
    Driver[] driverArray;
    ArrayList<Driver> drivers;
    Driver driverDescription;
    String season;
    Toolbar toolbarDriver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);

        // Initialize values
        driverArray = (Driver[]) this.getIntent().getSerializableExtra("drivers");
        season = this.getIntent().getStringExtra("season");
        drivers = new ArrayList<>(Arrays.asList(driverArray));

        toolBar();

        showAdapter();
    }

    public void toolBar() {
        toolbarDriver = (Toolbar) findViewById(R.id.toolbar_custom);
        setSupportActionBar(toolbarDriver);
        getSupportActionBar().setTitle("Driver Information " + season);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_home:
                Intent intent = new Intent(getBaseContext(), OverviewActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_custom, menu);
        setTitle("Driver information"+ season);
        return true;
    }

    public void showAdapter() {
        ListAdapter myAdapter = new DriverAdapter(this, drivers);

        // Initialize view
        driverView = (ListView) findViewById(R.id.driverView);
        assert driverView != null;

        driverView.setAdapter(myAdapter);

        driverView.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        driverDescription = drivers.get(position);
                        DriverDescriptionIntent(driverDescription);
                    }
                }
        );

    }

    // This will invoke the driverDescription Activity
    public void DriverDescriptionIntent(Driver driverDescription) {
        Intent driversIntent = new Intent(getApplicationContext(), DriverDescriptionActivity.class);
        driversIntent.putExtra("driverDescription", driverDescription);
        startActivity(driversIntent);
    }
}

