package com.example.joeribes.joeribes_pset6;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;



public class DriverResultsAdapter extends ArrayAdapter<DriverResults> {
    Context customContext;

    public DriverResultsAdapter(Context context, ArrayList<DriverResults> activities) {
        super(context, R.layout.result_row , activities);
        customContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater myInflater = LayoutInflater.from(getContext());

        View customView = convertView;
        if(customView == null){
            customView = myInflater.inflate(R.layout.result_row, parent, false);
        }

        String positionDriver = getItem(position).getPosition();
        String driver = getItem(position).getDriver();
        String constructor = getItem(position).getConstructor();
        String time = getItem(position).getTime();

        if(time.equals(" ")) {
            time = getItem(position).getStatus();
        }



        final TextView positionView = (TextView) customView.findViewById(R.id.counter);
        final TextView item_titleView = (TextView) customView.findViewById(R.id.item_title);
        final TextView timeView = (TextView) customView.findViewById(R.id.time);


        positionView.setText(positionDriver);
        item_titleView.setText(driver + " - " + constructor);
        timeView.setText(time);

        return customView;
    }
}