package com.example.joeribes.joeribes_pset6;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class DriverDescriptionActivity extends AppCompatActivity {

    TextView numberView, dateOfBirthView, nationalityView, urlView, url2;
    Driver driver;
    Toolbar toolbarDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_description);

        initializeViews();

        // Receive the driver intent
        driver = (Driver) this.getIntent().getSerializableExtra("driverDescription");

        setTextFields();
        toolBar();

    }

    // Initialize Views
    public void initializeViews() {
        numberView = (TextView) findViewById(R.id.numberView);
        dateOfBirthView = (TextView) findViewById(R.id.dateOfBirthView);
        nationalityView = (TextView) findViewById(R.id.nationalityView);
        urlView = (TextView) findViewById(R.id.urlView);
        url2 = (TextView) findViewById(R.id.url2);

    }

    // Set the information in the Textfields
    public void setTextFields() {
        numberView.setText("Permanent driver number: " + driver.getDriverNumber());
        dateOfBirthView.setText("Date of birth: " + driver.getDateOfBirth());
        nationalityView.setText("Nationality: " + driver.getNationality());
        urlView.setText("More information found at: ");
        url2.setText(driver.getWikiurl());
    }


    public void toolBar() {
        toolbarDescription = (Toolbar) findViewById(R.id.toolbar_custom);
        setSupportActionBar(toolbarDescription);
        getSupportActionBar().setTitle(driver.getName());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_custom, menu);
        setTitle(driver.getName());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_home:
                Intent intent = new Intent(getBaseContext(), OverviewActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }






}
