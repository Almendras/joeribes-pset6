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

        //private String season, round, position, points, wins, driver, constructor;

        // String season = getItem(position).getSeason();
        String season = getItem(position).getSeason();
        String round = getItem(position).getRound();
        String positionDriver = getItem(position).getPosition();
        String points = getItem(position).getPoints();
        String wins = getItem(position).getWins();
        String driver = getItem(position).getDriver();
        String constructor = getItem(position).getConstructor();
        String item_title = driver + " - " + constructor;



        final TextView counterView = (TextView) customView.findViewById(R.id.counter);
        final TextView item_titleView = (TextView) customView.findViewById(R.id.item_title);
        final TextView timeView = (TextView) customView.findViewById(R.id.points);
        final TextView dateView = (TextView) customView.findViewById(R.id.wins);

        counterView.setText(positionDriver);
        item_titleView.setText(item_title);
        timeView.setText(points);
        dateView.setText(wins);

        return customView;
    }
}
