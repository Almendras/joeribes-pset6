package com.example.joeribes.joeribes_pset6;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class DriverResultsActivity extends AppCompatActivity {

    ListView driverResultsView;
    DriverResults[] driverResultsArray;
    ArrayList<DriverResults> driverResults;
    String season;
    Toolbar toolbarResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_results);

        // Retrieve intents
        driverResultsArray = (DriverResults[]) this.getIntent().getSerializableExtra("driverResults");
        driverResults = new ArrayList<>(Arrays.asList(driverResultsArray));
        season = this.getIntent().getStringExtra("season");

        toolBar();

        showAdapter();
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
        return true;
    }

    public void toolBar() {
        toolbarResults = (Toolbar) findViewById(R.id.toolbar_custom);
        setSupportActionBar(toolbarResults);
        getSupportActionBar().setTitle("Race results " + season);
    }

    public void showAdapter() {
        ListAdapter myAdapter = new DriverResultsAdapter(this, driverResults);

        // Initialize view
        driverResultsView = (ListView) findViewById(R.id.driverResultView);
        assert driverResultsView != null;

        driverResultsView.setAdapter(myAdapter);
    }
}
