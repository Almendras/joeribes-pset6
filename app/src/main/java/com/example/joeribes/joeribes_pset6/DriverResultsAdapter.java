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
    String positionDriver, driver, constructor, time;
    TextView positionView, item_titleView, timeView;

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

        getInformation(position);
        initializeViews(customView);
        setTextFields();

        return customView;
    }

    // Get the correct information
    public void getInformation(int position) {
        positionDriver = getItem(position).getPosition();
        driver = getItem(position).getDriver();
        constructor = getItem(position).getConstructor();
        time = getItem(position).getTime();

        // Get the status of the driver instead of the time if there is no time available
        if(time.equals(" ")) {
            time = getItem(position).getStatus();
        }
    }

    // Initialize the views
    public void initializeViews(View customView) {
        positionView = (TextView) customView.findViewById(R.id.counter);
        item_titleView = (TextView) customView.findViewById(R.id.item_title);
        timeView = (TextView) customView.findViewById(R.id.time);
    }

    // Set the information in the Textfields
    public void setTextFields() {
        positionView.setText(positionDriver);
        item_titleView.setText(driver + " - " + constructor);
        timeView.setText(time);
    }


}