package com.example.joeribes.joeribes_pset6;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class DriverActivity extends AppCompatActivity {

    ListView driverView;
    Driver[] driverArray;
    ArrayList<Driver> drivers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);

        driverArray = (Driver[]) this.getIntent().getSerializableExtra("drivers");
        drivers = new ArrayList<>(Arrays.asList(driverArray));

        showAdapter();
    }

    public void showAdapter() {
        ListAdapter myAdapter = new DriverAdapter(this, drivers);

        // Initialize view
        driverView = (ListView) findViewById(R.id.driverView);
        assert driverView != null;

        driverView.setAdapter(myAdapter);
    }
}

