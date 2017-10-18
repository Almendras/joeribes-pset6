package com.example.joeribes.joeribes_pset6;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class RaceScheduleActivity extends AppCompatActivity {

    ListView raceSchedulesView;
    RaceSchedule[] raceSchedulesArray;
    ArrayList<RaceSchedule> raceSchedules;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_race_schedule);

        raceSchedulesArray = (RaceSchedule[]) this.getIntent().getSerializableExtra("raceSchedule");
        raceSchedules = new ArrayList<>(Arrays.asList(raceSchedulesArray));

        showAdapter();
    }

    public void showAdapter() {
        ListAdapter myAdapter = new RaceScheduleAdapter(this, raceSchedules);

        // Initialize view
        raceSchedulesView = (ListView) findViewById(R.id.raceScheduleView);
        assert raceSchedulesView != null;

        raceSchedulesView.setAdapter(myAdapter);
    }
}
