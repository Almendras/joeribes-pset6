package com.example.joeribes.joeribes_pset6;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;


public class StandingsAdapter extends ArrayAdapter<Standings> {
    Context customContext;

    public StandingsAdapter(Context context, ArrayList<Standings> activities) {
        super(context, R.layout.standings_row , activities);
        customContext = context;
    }
}
