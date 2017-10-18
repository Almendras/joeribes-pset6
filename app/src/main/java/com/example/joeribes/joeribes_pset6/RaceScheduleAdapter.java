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

        // String season = getItem(position).getSeason();
        String round = getItem(position).getRound();
        String trackName = getItem(position).getRaceName();
        String time = getItem(position).getTime();
        String date = getItem(position).getDate();



        final TextView roundView = (TextView) customView.findViewById(R.id.counter);
        final TextView item_titleView = (TextView) customView.findViewById(R.id.item_title);
        final TextView timeView = (TextView) customView.findViewById(R.id.time);
        final TextView dateView = (TextView) customView.findViewById(R.id.date);

        roundView.setText(round);
        item_titleView.setText(trackName);
        timeView.setText(time);
        dateView.setText(date);

        return customView;
    }


}