package com.example.joeribes.joeribes_pset6;


import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class StandingsAsyncTaskAdd extends AsyncTask<String, Integer, String> {
    Context context;
    AddItemActivity addItemAct;
    Standings[] standing;

    public StandingsAsyncTaskAdd(AddItemActivity addItem) {
        this.addItemAct = addItem;
        this.context = this.addItemAct.getApplicationContext();
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
                String season = racesObj.getString("season");
                String round = racesObj.getString("round");

                JSONArray DriverStandingsObj = racesObj.getJSONArray("DriverStandings");
                standing = new Standings[DriverStandingsObj.length()];


                // Get all position data
                for(int i = 0; i < DriverStandingsObj.length(); i++) {
                    JSONObject DriverStandings = DriverStandingsObj.getJSONObject(i);

                    // Retrieve the position, points, wins
                    String position = DriverStandings.getString("position");
                    String points = DriverStandings.getString("points");
                    String wins = DriverStandings.getString("wins");

                    // Retrieve the driver givenName and familyName
                    JSONObject Driver = DriverStandings.getJSONObject("Driver");
                    String givenName = Driver.getString("givenName");
                    String familyName = Driver.getString("familyName");
                    String driver = givenName + " " + familyName;

                    // Retrieve the constructor
                    JSONArray Constructor = DriverStandings.getJSONArray("Constructors");
                    //JSONObject Constructor = DriverStandings.getJSONObject("Constructors");
                    JSONObject ConstructorObj = Constructor.getJSONObject(0);
                    String constructor = ConstructorObj.getString("name");

                    standing[i] = new Standings(season, round, position, points, wins, driver, constructor);
                }



            } catch (JSONException e) {
                e.printStackTrace();
            }

            this.addItemAct.startIntentStandings(standing);
        }
    }
}
