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
                //JSONObject Races = RaceTable.getJSONObject("Races");
                JSONArray Races = RaceTable.getJSONArray("Races");

                JSONObject racesObj = Races.getJSONObject(0);
                String season= racesObj.getString("season");
                String race_name= racesObj.getString("raceName");

                //String race_name = Races.getJSONObject(3).toString();

                JSONArray raceResultsObj = racesObj.getJSONArray("Results");
                driverResults = new DriverResults[raceResultsObj.length()];

                // Get all position data
                for(int i = 0; i < raceResultsObj.length(); i++) {
                    JSONObject raceResult = raceResultsObj.getJSONObject(i);

                    // Position of the driver
                    String position = raceResult.getString("position");

                    // Retrieve the driver givenName and familyName
                    JSONObject Driver = raceResult.getJSONObject("Driver");
                    String givenName = Driver.getString("givenName");
                    String familyName = Driver.getString("familyName");
                    String driver = givenName + " " + familyName;


                    // Retrieve the constructor
                    JSONObject Constructor = raceResult.getJSONObject("Constructor");
                    String constructor = Constructor.getString("name");

                    // Status
                    String status = raceResult.getString("status");

                    // Time
                    String time = " ";
                    if(raceResult.has("Time")) {
                        JSONObject Time = raceResult.getJSONObject("Time");
                        time = Time.getString("time");
                    }

                    driverResults[i] = new DriverResults(race_name, season, driver, position, constructor, time, status);
                }



            } catch (JSONException e) {
                e.printStackTrace();
            }

            this.overAct.startIntentDriverResults(driverResults);
        }
    }
}
