package com.example.googlemapsapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

/**
 * MainActivity serves as the central navigation hub for the application.
 * It provides access to various features such as map display, points of interest,
 * and user location tracking by routing the user to the appropriate activity based
 * on the selected option.
 */
public class MainActivity extends AppCompatActivity {

    /**
     * onCreate is the entry point of this activity.
     * It sets up the UI and defines the actions for each button.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being
     * shut down, this Bundle contains the data it most recently supplied.
     * Otherwise, it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Call the superclass implementation to ensure proper initialization.
        super.onCreate(savedInstanceState);

        // Define which layout file to use for this activity's UI.
        setContentView(R.layout.activity_main);

        // Link the buttons from the layout to variables.
        Button mapBtn = findViewById(R.id.mapButton);
        Button poiBtn = findViewById(R.id.poiButton);
        Button locationBtn = findViewById(R.id.locationButton);

        // Set the click listener for the Map button.
        // Launches GoogleMapsActivity when the button is clicked.
        mapBtn.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, GoogleMapsActivity.class)));

        // Set the click listener for the POI button.
        // Launches PoiActivity when the button is clicked.
        poiBtn.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, PoiActivity.class)));

        // Set the click listener for the Location button.
        // Launches LocationActivity when the button is clicked.
        locationBtn.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, LocationActivity.class)));
    }
}