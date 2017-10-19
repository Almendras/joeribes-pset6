package com.example.joeribes.joeribes_pset6;


import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DriverResultsAsyncTask extends AsyncTask<String, Integer, String> {
    Context context;
    OverviewActivity overAct;
    DriverResults[] driverResults;

    public DriverResultsAsyncTask(OverviewActivity main) {
        this.overAct = main;
        this.context = this.overAct.getApplicationContext();
    }

    @Override
    protected void onPreExecute() {
        Toast.makeText(context, "Searching for standings...", Toast.LENGTH_SHORT).show();
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

                JSONObject racesObj = Races.getJSONObject(0);
                String race_name= racesObj.getString("raceName");

                JSONArray raceResults = racesObj.getJSONArray("Results");

                driverResults = createDriverResultsArray(raceResults, race_name);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            this.overAct.startIntentDriverResults(driverResults);
        }
    }

    private DriverResults[] createDriverResultsArray(JSONArray raceResults, String race_name) {
        driverResults = new DriverResults[raceResults.length()];

        try {
            // Get all position data
            for(int i = 0; i < raceResults.length(); i++) {
                JSONObject raceResult = raceResults.getJSONObject(i);

                // Position of the driver
                String position = raceResult.getString("position");

                // Retrieve the driver givenName and familyName
                JSONObject Driver = raceResult.getJSONObject("Driver");
                String givenName = Driver.getString("givenName");
                String familyName = Driver.getString("familyName");
                String driver = givenName + " " + familyName;

                // Status
                String status = raceResult.getString("status");

                // Time
                String time = " ";
                if(raceResult.has("Time")) {
                    JSONObject Time = raceResult.getJSONObject("Time");
                    time = Time.getString("time");
                }

                driverResults[i] = new DriverResults(race_name, driver, position, time, status);
            }

        } catch(JSONException e) {
            e.printStackTrace();
        }

        return driverResults;

    }
}
