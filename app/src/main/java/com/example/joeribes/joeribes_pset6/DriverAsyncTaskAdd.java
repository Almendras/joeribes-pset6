package com.example.joeribes.joeribes_pset6;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DriverAsyncTaskAdd extends AsyncTask<String, Integer, String> {
    Context context;
    AddItemActivity addItemAct;
    Driver[] driver;

    public DriverAsyncTaskAdd (AddItemActivity addItem) {
        this.addItemAct = addItem;
        this.context = this.addItemAct.getApplicationContext();
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
                JSONArray Drivers = DriverTable.getJSONArray("Drivers");

                driver = createDriverArray(Drivers);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            this.addItemAct.startIntentDrivers(driver);
        }
    }

    private Driver[] createDriverArray(JSONArray Drivers) {
        driver = new Driver[Drivers.length()];

        try {
            for(int i = 0; i < Drivers.length(); i++) {
                JSONObject driverObj = Drivers.getJSONObject(i);

                // Retrieve the driver givenName and familyName
                String givenName = driverObj.getString("givenName");
                String familyName = driverObj.getString("familyName");
                String driverName = givenName + " " + familyName;

                // Retrieve the driver Code
                String driverCode = driverObj.getString("code");

                // Retrieve the driver Number
                String driverNumber = " ";
                if (driverObj.has("permanentNumber")) {
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

        return driver;

    }
}
