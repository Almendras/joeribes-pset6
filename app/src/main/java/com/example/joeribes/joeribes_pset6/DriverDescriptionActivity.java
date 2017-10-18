package com.example.joeribes.joeribes_pset6;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DriverDescriptionActivity extends AppCompatActivity {

    TextView nameView;
    TextView numberView;
    TextView dateOfBirthView;
    TextView nationalityView;
    TextView urlView;
    Driver driver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_description);

        // Initialize Views
        nameView = (TextView) findViewById(R.id.nameView);
        numberView = (TextView) findViewById(R.id.numberView);
        dateOfBirthView = (TextView) findViewById(R.id.dateOfBirthView);
        nationalityView = (TextView) findViewById(R.id.nationalityView);
        urlView = (TextView) findViewById(R.id.urlView);

        // Receive the driver intent
        driver = (Driver) this.getIntent().getSerializableExtra("driverDescription");

        // Fill the texts of the drivers
        nameView.setText(driver.getName() + " - " + driver.getDriverCode());
        numberView.setText("Permanent driver number: " + driver.getDriverNumber());
        dateOfBirthView.setText("Date of birth: " + driver.getDateOfBirth());
        nationalityView.setText("Nationality: " + driver.getNationality());
        urlView.setText("Wikipedia link: " + driver.getWikiurl());
    }
}
