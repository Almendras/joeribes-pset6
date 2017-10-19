package com.example.joeribes.joeribes_pset6;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class DriverResultsActivity extends AppCompatActivity {

    ListView driverResultsView;
    DriverResults[] driverResultsArray;
    ArrayList<DriverResults> driverResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_results);

        // Retrieve intents
        driverResultsArray = (DriverResults[]) this.getIntent().getSerializableExtra("driverResults");
        driverResults = new ArrayList<>(Arrays.asList(driverResultsArray));

        showAdapter();
    }

    public void showAdapter() {
        ListAdapter myAdapter = new DriverResultsAdapter(this, driverResults);

        // Initialize view
        driverResultsView = (ListView) findViewById(R.id.driverResultView);
        assert driverResultsView != null;

        driverResultsView.setAdapter(myAdapter);
    }
}
