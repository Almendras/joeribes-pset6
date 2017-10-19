package com.example.joeribes.joeribes_pset6;


import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class StandingsAsyncTask extends AsyncTask<String, Integer, String> {
    Context context;
    OverviewActivity overAct;
    Standings[] standing;

    public StandingsAsyncTask(OverviewActivity main) {
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
                JSONObject StandingsTable = MRData.getJSONObject("StandingsTable");
                JSONArray StandingsLists = StandingsTable.getJSONArray("StandingsLists");

                JSONObject racesObj = StandingsLists.getJSONObject(0);
                JSONArray DriverStandings =  racesObj.getJSONArray("DriverStandings");

                standing = createStandingsArray(DriverStandings);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            this.overAct.startIntentStandings(standing);
        }
    }

    private Standings[] createStandingsArray(JSONArray DriverStandings) {
        standing = new Standings[DriverStandings.length()];
        try {
            // Get all position data
            for(int i = 0; i < DriverStandings.length(); i++) {
                JSONObject DriverStanding = DriverStandings.getJSONObject(i);

                // Retrieve the position, points, wins
                String position = DriverStanding.getString("position");
                String points = DriverStanding.getString("points");
                String wins = DriverStanding.getString("wins");

                // Retrieve the driver givenName and familyName
                JSONObject Driver = DriverStanding.getJSONObject("Driver");
                String givenName = Driver.getString("givenName");
                String familyName = Driver.getString("familyName");
                String driver = givenName + " " + familyName;

                // Retrieve the constructor
                JSONArray Constructor = DriverStanding.getJSONArray("Constructors");

                JSONObject ConstructorObj = Constructor.getJSONObject(0);
                String constructor = ConstructorObj.getString("name");

                standing[i] = new Standings(position, points, wins, driver, constructor);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return standing;
    }
}
