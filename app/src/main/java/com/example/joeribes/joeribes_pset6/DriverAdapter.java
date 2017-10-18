package com.example.joeribes.joeribes_pset6;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;


public class DriverAdapter extends ArrayAdapter<Driver> {
    Context customContext;

    public DriverAdapter(Context context, ArrayList<Driver> activities) {
        super(context, R.layout.driver_row , activities);
        customContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater myInflater = LayoutInflater.from(getContext());

        View customView = convertView;
        if(customView == null){
            customView = myInflater.inflate(R.layout. driver_row, parent, false);
        }

        // Retrieve the correct values for the adapter
        String name = getItem(position).getName();
        String driverCode = getItem(position).getDriverCode();
        String driverNumber = getItem(position).getDriverNumber();

        // Initialize the Views
        final TextView driverNumberView = (TextView) customView.findViewById(R.id.driverNumber);
        final TextView item_titleView = (TextView) customView.findViewById(R.id.item_title);

        // Set the text of the Views
        driverNumberView.setText(driverNumber);
        item_titleView.setText(name + " - " + driverCode);

        return customView;
    }
}