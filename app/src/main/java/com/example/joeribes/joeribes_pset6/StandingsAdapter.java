package com.example.joeribes.joeribes_pset6;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class StandingsAdapter extends ArrayAdapter<Standings> {
    Context customContext;
    String positionDriver, points, wins, driver, constructor, item_title;
    TextView counterView, item_titleView, timeView, dateView;

    public StandingsAdapter(Context context, ArrayList<Standings> activities) {
        super(context, R.layout.standings_row , activities);
        customContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater myInflater = LayoutInflater.from(getContext());

        View customView = convertView;
        if(customView == null){
            customView = myInflater.inflate(R.layout.standings_row, parent, false);
        }

        getInformation(position);
        initializeViews(customView);
        setTextFields();

        return customView;
    }

    // Get the correct information
    public void getInformation(int position) {
        positionDriver = getItem(position).getPosition();
        points = getItem(position).getPoints();
        wins = getItem(position).getWins();
        driver = getItem(position).getDriver();
        constructor = getItem(position).getConstructor();
        item_title = driver + " - " + constructor;
    }

    // Initialize the views
    public void initializeViews(View customView) {
        counterView = (TextView) customView.findViewById(R.id.counter);
        item_titleView = (TextView) customView.findViewById(R.id.item_title);
        timeView = (TextView) customView.findViewById(R.id.points);
        dateView = (TextView) customView.findViewById(R.id.wins);
    }

    // Set the information in the Textfields
    public void setTextFields() {
        counterView.setText(positionDriver);
        item_titleView.setText(item_title);
        timeView.setText(points);
        dateView.setText(wins);
    }
}
