package com.example.joeribes.joeribes_pset6;


import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RaceScheduleAsyncTaskAdd extends AsyncTask<String, Integer, String> {
    Context context;
    AddItemActivity addItemAct;
    RaceSchedule[] raceSchedule;

    public RaceScheduleAsyncTaskAdd(AddItemActivity addItem) {
        this.addItemAct = addItem;
        this.context = this.addItemAct.getApplicationContext();
    }

    @Override
    protected void onPreExecute() {
        Toast.makeText(context, "Searching for schedules...", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected String doInBackground(String... params) {
        return HttpRequestHelper.downloadFromServer(params);
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        if(result.length() == 0) {
            Toast.makeText(context, "Something went wrong getting the data from server", Toast.LENGTH_SHORT).show();
        }
        else {
            try {
                JSONObject formula1StreamObj = new JSONObject(result);
                JSONObject MRData = formula1StreamObj.getJSONObject("MRData");
                JSONObject RaceTable = MRData.getJSONObject("RaceTable");
                JSONArray Races = RaceTable.getJSONArray("Races");

                raceSchedule = createRaceScheduleArray(Races);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            this.addItemAct.startIntentRaceSchedule(raceSchedule);
        }
    }

    private RaceSchedule[] createRaceScheduleArray(JSONArray Races) {
        raceSchedule = new RaceSchedule[Races.length()];

        try {
            for (int i = 0; i < Races.length(); i++) {
                JSONObject raceScheduleObj = Races.getJSONObject(i);

                String season = raceScheduleObj.getString("season");
                String round = raceScheduleObj.getString("round");
                String raceName = raceScheduleObj.getString("raceName");
                String time = raceScheduleObj.getString("time");
                String date = raceScheduleObj.getString("date");
                raceSchedule[i] = new RaceSchedule(season, round, raceName, time, date);
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return raceSchedule;
    }
}
