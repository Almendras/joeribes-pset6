package com.example.joeribes.joeribes_pset6;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class StandingsActivity extends AppCompatActivity {

    ListView standingsView;
    Standings[] standingsArray;
    ArrayList<Standings> standings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standings);

        standingsArray = (Standings[]) this.getIntent().getSerializableExtra("standings");
        standings = new ArrayList<>(Arrays.asList(standingsArray));

        showAdapter();
    }

    public void showAdapter() {
        ListAdapter myAdapter = new StandingsAdapter(this, standings);

        // Initialize view
        standingsView = (ListView) findViewById(R.id.standingsView);
        assert standingsView != null;

        standingsView.setAdapter(myAdapter);
    }
}
