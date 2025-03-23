package com.example.googlemapsapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

/**
 * LocationActivity displays the user interface for location-based features.
 * It serves as the entry point for location-related functionality,
 * loading the corresponding layout and preparing the activity for user interaction.
 */
public class LocationActivity extends AppCompatActivity {

    /**
     * onCreate is called when the activity is first created.
     * It initializes the activity and sets the content view to the layout defined in activity_location.xml.
     *
     * @param savedInstanceState Bundle containing the activity's previously saved state, if any.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the UI layout for this activity using the XML resource.
        setContentView(R.layout.activity_location);
    }
}