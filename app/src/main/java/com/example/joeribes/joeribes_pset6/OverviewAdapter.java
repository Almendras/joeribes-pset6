package com.example.joeribes.joeribes_pset6;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;



public class OverviewAdapter extends ArrayAdapter<String> {
    Context customContext;

    public OverviewAdapter(Context context, ArrayList<String> activities) {
        super(context, R.layout.overview_row , activities);
        customContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater myInflater = LayoutInflater.from(getContext());

        View customView = convertView;
        if(customView == null){
            customView = myInflater.inflate(R.layout.overview_row, parent, false);
        }



        String item = getItem(position);


        final TextView myText = (TextView) customView.findViewById(R.id.overviewTextView);

        myText.setText(item);
        return customView;
    }


}