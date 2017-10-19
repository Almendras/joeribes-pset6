package com.example.joeribes.joeribes_pset6;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;



public class RaceScheduleAdapter extends ArrayAdapter<RaceSchedule> {
    Context customContext;
    String round, trackName, time, date;
    TextView roundView, item_titleView, timeView, dateView;

    public RaceScheduleAdapter(Context context, ArrayList<RaceSchedule> activities) {
        super(context, R.layout.schedule_row , activities);
        customContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater myInflater = LayoutInflater.from(getContext());

        View customView = convertView;
        if(customView == null){
            customView = myInflater.inflate(R.layout.schedule_row, parent, false);
        }

        getInformation(position);
        initializeViews(customView);
        setTextFields();

        return customView;
    }

    // Retrieve the correct information
    public void getInformation(int position) {
        // season = getItem(position).getSeason();
        round = getItem(position).getRound();
        trackName = getItem(position).getRaceName();
        time = getItem(position).getTime();
        date = getItem(position).getDate();
    }

    // Initialize the views
    public void initializeViews(View customView) {
        roundView = (TextView) customView.findViewById(R.id.counter);
        item_titleView = (TextView) customView.findViewById(R.id.item_title);
        timeView = (TextView) customView.findViewById(R.id.time);
        dateView = (TextView) customView.findViewById(R.id.date);
    }

    // Set the information in the Textfields
    public void setTextFields() {
        roundView.setText(round);
        item_titleView.setText(trackName);
        timeView.setText(time);
        dateView.setText(date);
    }


}