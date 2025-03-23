package com.example.googlemapsapp;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import androidx.fragment.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.googlemapsapp.databinding.ActivityGoogleMapsBinding;

/**
 * GoogleMapsActivity displays a Google Map with various markers, overlays, and interactive controls.
 * It allows users to switch between different map types, adjust zoom levels, and navigate to predefined locations.
 */
public class GoogleMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    // Instance of GoogleMap to control map operations.
    private GoogleMap mMap;
    // Variable to track the current zoom level.
    private float zoomvar = 10;

    // Predefined geographic coordinates for key locations.
    private final LatLng SofiaCenter = new LatLng(42.6977082, 23.3218675);
    private final LatLng TUsofia = new LatLng(42.6570607, 23.3551086);
    private final LatLng SofiaLibrary = new LatLng(42.696897, 23.325877);
    private final LatLng SofiaUniversity = new LatLng(42.693978, 23.334181);
    private final LatLng IvanVazovTheater = new LatLng(42.694978, 23.324604);

    /**
     * onCreate initializes the activity and sets up the map fragment.
     *
     * @param savedInstanceState Bundle containing the previous state, if any.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the layout defined in activity_google_maps.xml.
        setContentView(R.layout.activity_google_maps);

        // Retrieve the SupportMapFragment and request the map asynchronously.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        // If the map fragment is available, initialize it.
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     *
     * @param googleMap The GoogleMap instance provided by the callback.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add markers for the center and other key locations.
        mMap.addMarker(new MarkerOptions().position(SofiaCenter).title("Marker in Sofia Center"));
        mMap.addMarker(new MarkerOptions().position(TUsofia).title("Marker at TU Sofia"));
        mMap.addMarker(new MarkerOptions().position(SofiaLibrary).title("Sofia Library"));
        mMap.addMarker(new MarkerOptions().position(SofiaUniversity).title("Sofia University"));
        mMap.addMarker(new MarkerOptions().position(IvanVazovTheater).title("Ivan Vazov Theater"));

        // Move the camera to Sofia Center with a zoom level of 10.
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SofiaCenter, 10));

        // Retrieve buttons from the layout.
        Button plusZOOM = findViewById(R.id.zoomIN);
        Button minusZOOM = findViewById(R.id.zoomOUT);
        Button hybrid = findViewById(R.id.hybridBUT);
        Button normal = findViewById(R.id.normalBUT);
        Button satellite = findViewById(R.id.satelliteBUT);
        Button terrain = findViewById(R.id.terrainBUT);
        Button tus = findViewById(R.id.tusBUT);
        Button back = findViewById(R.id.BACK);
        Button capitalLibraryBut = findViewById(R.id.capitalLibraryBut);
        Button sofiaUniversityBut = findViewById(R.id.sofiaUniversityBut);
        Button theaterBut = findViewById(R.id.theaterBut);

        // Create and add a circle overlay centered at TU Sofia.
        CircleOptions circleOptions = new CircleOptions()
                .center(TUsofia)
                .radius(25)
                .fillColor(Color.BLUE)
                .strokeColor(Color.RED)
                .strokeWidth(4);

        mMap.addCircle(circleOptions);

        // Set the click listener for the Zoom In button.
        // Increases the zoom level (up to a maximum of 21) and animates the camera.
        plusZOOM.setOnClickListener(v -> {
            zoomvar = Math.min(21, zoomvar + 1); // Ensure zoom doesn't exceed 21
            mMap.animateCamera(CameraUpdateFactory.zoomTo(zoomvar));
        });

        // Set the click listener for the Zoom Out button.
        // Decreases the zoom level (minimum of 2) and animates the camera.
        minusZOOM.setOnClickListener(v -> {
            zoomvar = Math.max(2, zoomvar - 1); // Ensure zoom doesn't go below 2
            mMap.animateCamera(CameraUpdateFactory.zoomTo(zoomvar));
        });

        // Set the click listener for the Hybrid View button.
        // Switches the map display to a hybrid view (satellite imagery with roads).
        hybrid.setOnClickListener(v -> mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID));

        // Set the click listener for the Normal View button.
        // Switches the map display to a standard roadmap view.
        normal.setOnClickListener(v -> mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL));

        // Set the click listener for the Satellite View button.
        // Switches the map display to a satellite view.
        satellite.setOnClickListener(v -> mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE));

        // Set the click listener for the Terrain View button.
        // Switches the map display to a terrain view.
        terrain.setOnClickListener(v -> mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN));

        // Set the click listener for the TU Sofia button.
        // Animates the camera to TU Sofia with a zoom level of 15.
        tus.setOnClickListener(v -> mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(TUsofia, 15)));

        // Set the click listener for the Library button.
        // Animates the camera to Sofia Library with a zoom level of 15.
        capitalLibraryBut.setOnClickListener(v -> mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(SofiaLibrary, 15)));

        // Set the click listener for the Sofia University button.
        // Animates the camera to Sofia University with a zoom level of 15.
        sofiaUniversityBut.setOnClickListener(v -> mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(SofiaUniversity, 15)));

        // Set the click listener for the Theater button.
        // Animates the camera to Ivan Vazov Theater with a zoom level of 15.
        theaterBut.setOnClickListener(v -> mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(IvanVazovTheater, 15)));

        // Set the click listener for the Back button.
        // Returns to MainActivity and closes the current activity.
        back.setOnClickListener(v -> {
            Intent intent = new Intent(GoogleMapsActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
