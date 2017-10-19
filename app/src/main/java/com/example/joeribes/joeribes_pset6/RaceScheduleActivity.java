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

public class RaceScheduleActivity extends AppCompatActivity {

    ListView raceSchedulesView;
    RaceSchedule[] raceSchedulesArray;
    ArrayList<RaceSchedule> raceSchedules;
    Toolbar schedule;
    String season;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_race_schedule);

        raceSchedulesArray = (RaceSchedule[]) this.getIntent().getSerializableExtra("raceSchedule");
        season = this.getIntent().getStringExtra("season");
        raceSchedules = new ArrayList<>(Arrays.asList(raceSchedulesArray));

        toolBar();

        showAdapter();
    }

    public void toolBar() {
        schedule = (Toolbar) findViewById(R.id.toolbar_custom);
        setSupportActionBar(schedule);
        getSupportActionBar().setTitle("Race Schedule " + season);
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
        ListAdapter myAdapter = new RaceScheduleAdapter(this, raceSchedules);

        // Initialize view
        raceSchedulesView = (ListView) findViewById(R.id.raceScheduleView);
        assert raceSchedulesView != null;

        raceSchedulesView.setAdapter(myAdapter);
    }
}
