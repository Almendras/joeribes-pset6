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

public class StandingsActivity extends AppCompatActivity {

    ListView standingsView;
    Standings[] standingsArray;
    ArrayList<Standings> standings;
    Toolbar toolbarStandings;
    String season;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standings);

        standingsArray = (Standings[]) this.getIntent().getSerializableExtra("standings");
        season = this.getIntent().getStringExtra("season");
        standings = new ArrayList<>(Arrays.asList(standingsArray));

        toolBar();

        showAdapter();
    }

    public void toolBar() {
        toolbarStandings = (Toolbar) findViewById(R.id.toolbar_custom);
        setSupportActionBar(toolbarStandings);
        getSupportActionBar().setTitle("Driver standings " + season);
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

    public void showAdapter() {
        ListAdapter myAdapter = new StandingsAdapter(this, standings);

        // Initialize view
        standingsView = (ListView) findViewById(R.id.standingsView);
        assert standingsView != null;

        standingsView.setAdapter(myAdapter);
    }
}
