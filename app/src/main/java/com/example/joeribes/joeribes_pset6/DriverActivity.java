package com.example.joeribes.joeribes_pset6;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);

        // Initialize values
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

        driverView.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Driver driverDescription = drivers.get(position);

                        Intent driversIntent = new Intent(getApplicationContext(), DriverDescriptionActivity.class);
                        driversIntent.putExtra("driverDescription", driverDescription);
                        startActivity(driversIntent);

                    }
                }
        );

    }
}

