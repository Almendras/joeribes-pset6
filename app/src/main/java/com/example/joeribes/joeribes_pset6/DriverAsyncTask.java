package com.example.joeribes.joeribes_pset6;


import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DriverAsyncTask extends AsyncTask<String, Integer, String> {
    Context context;
    OverviewActivity overAct;
    Driver[] driver;

    public DriverAsyncTask(OverviewActivity over) {
        this.overAct = over;
        this.context = this.overAct.getApplicationContext();
    }

    @Override
    protected void onPreExecute() {
        Toast.makeText(context, "Searching for drivers...", Toast.LENGTH_SHORT).show();
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
                JSONObject DriverTable = MRData.getJSONObject("DriverTable");
                JSONArray DriversObj = DriverTable.getJSONArray("Drivers");

                driver = new Driver[DriversObj.length()];

                // Get all position data
                for(int i = 0; i < DriversObj.length(); i++) {
                    JSONObject driverObj = DriversObj.getJSONObject(i);

                    // Retrieve the driver givenName and familyName
                    String givenName = driverObj.getString("givenName");
                    String familyName = driverObj.getString("familyName");
                    String driverName = givenName + " " + familyName;

                    // Retrieve the driver Code
                    String driverCode = driverObj.getString("code");

                    // Retrieve the driver Number
                    String driverNumber = " ";
                    if(driverObj.has("permanentNumber")) {
                        driverNumber = driverObj.getString("permanentNumber");
                    }

                    // Retrieve the date of birth
                    String dateOfBirth = driverObj.getString("dateOfBirth");

                    // Retrieve the nationality
                    String nationality = driverObj.getString("nationality");

                    // Retrieve the wikiurl
                    String wikiurl = driverObj.getString("url");

                    driver[i] = new Driver(driverName, driverCode, driverNumber, dateOfBirth, nationality, wikiurl);
                }



            } catch (JSONException e) {
                e.printStackTrace();
            }

            this.overAct.startIntentDrivers(driver);
        }
    }
}
