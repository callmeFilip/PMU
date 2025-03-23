package com.example.googlemapsapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;
import android.widget.Button;
import androidx.fragment.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.*;

/**
 * PoiActivity displays a Google Map with multiple points of interest.
 * It allows users to interact with the map by moving the camera to specific locations,
 * adding custom markers, drawing lines and polygons, and controlling zoom levels.
 * This activity integrates various Google Maps functionalities and provides
 * an interactive UI for navigating through different landmarks.
 */
public class PoiActivity extends FragmentActivity implements OnMapReadyCallback {
    // GoogleMap instance for manipulating the map.
    private GoogleMap mMap;

    // Variable to track the current zoom level.
    private float zoomvar = 10;

    // Predefined coordinates for various locations.
    private final LatLng TUsofia = new LatLng(42.6570607, 23.3551086);
    private final LatLng unss = new LatLng(42.651266, 23.3466593);
    private final LatLng lty = new LatLng(42.6537179, 23.3564474);
    private final LatLng nsa = new LatLng(42.6484442, 23.3466905);

    private final LatLng SofiaLibrary = new LatLng(42.696897, 23.325877);
    private final LatLng SofiaUniversity = new LatLng(42.693978, 23.334181);
    private final LatLng IvanVazovTheater = new LatLng(42.694978, 23.324604);

    /**
     * onCreate initializes the activity, sets the layout, and prepares the map fragment.
     *
     * @param savedInstanceState Bundle containing the activity's previously saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the layout for this activity.
        setContentView(R.layout.activity_poi);

        // Obtain the SupportMapFragment and register the callback to be notified when the map is ready.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.location_map);

        // Check if the map fragment is available.
        if (mapFragment == null) {
            Log.e("PoiActivity", "SupportMapFragment is NULL!");
        } else {
            // Set the asynchronous callback to handle the map once it's ready.
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
        Log.i("PoiActivity", "onMapReady() TRIGGERED");
        mMap = googleMap;

        // Create a marker for TU Sofia with a custom icon and rotation.
        MarkerOptions markerOptions = new MarkerOptions()
                .position(TUsofia)
                .title("ТУ София")
                .icon(resizeBitmap(R.drawable.tu, 100, 100))
                .rotation(20)
                .draggable(false);

        mMap.addMarker(markerOptions);
        // Add markers for various points of interest with designated colors.
        // addMarker(TUsofia, "TU Sofia", BitmapDescriptorFactory.HUE_BLUE);
        addMarker(unss, "UNSS", BitmapDescriptorFactory.HUE_RED);
        addMarker(lty, "LTY", BitmapDescriptorFactory.HUE_YELLOW);
        addMarker(nsa, "HCA", BitmapDescriptorFactory.HUE_GREEN);
        addMarker(SofiaLibrary, "Sofia Library", BitmapDescriptorFactory.HUE_ORANGE);
        addMarker(SofiaUniversity, "Sofia University", BitmapDescriptorFactory.HUE_AZURE);
        addMarker(IvanVazovTheater, "Ivan Vazov Theater", BitmapDescriptorFactory.HUE_VIOLET);

        // Move the camera to TU Sofia with a zoom level of 14.
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(TUsofia, 14));

        // Set the click listener for the TU Sofia button.
        // Moves the camera to TU Sofia when the button is clicked.
        Button tusofiaB = findViewById(R.id.tusofia);
        tusofiaB.setOnClickListener(v -> moveCamera(TUsofia));

        // Set the click listener for the UNSS button.
        // Moves the camera to UNSS when the button is clicked.
        Button unssB = findViewById(R.id.unss);
        unssB.setOnClickListener(v -> moveCamera(unss));

        // Set the click listener for the LTY button.
        // Moves the camera to LTY when the button is clicked.
        Button ltyB = findViewById(R.id.lty);
        ltyB.setOnClickListener(v -> moveCamera(lty));

        // Set the click listener for the NSA button.
        // Moves the camera to HCA (NSA) when the button is clicked.
        Button nsaB = findViewById(R.id.nsa);
        nsaB.setOnClickListener(v -> moveCamera(nsa));

        // Set the click listener for the Sofia Library button.
        // Moves the camera to Sofia Library when the button is clicked.
        Button sofiaLibraryB = findViewById(R.id.sofiaLibrary);
        sofiaLibraryB.setOnClickListener(v -> moveCamera(SofiaLibrary));

        // Set the click listener for the Sofia University button.
        // Moves the camera to Sofia University when the button is clicked.
        Button sofiaUniversityB = findViewById(R.id.sofiaUniversity);
        sofiaUniversityB.setOnClickListener(v -> moveCamera(SofiaUniversity));

        // Set the click listener for the Ivan Vazov Theater button.
        // Moves the camera to Ivan Vazov Theater when the button is clicked.
        Button ivanVazovB = findViewById(R.id.ivanVazov);
        ivanVazovB.setOnClickListener(v -> moveCamera(IvanVazovTheater));

        // Set the click listener for the Zoom In button.
        // Increments the zoom level (up to a maximum of 21) and animates the camera zoom.
        Button plusZOOM = findViewById(R.id.zoomIN);
        plusZOOM.setOnClickListener(v -> {
            zoomvar = Math.min(21, zoomvar + 1);
            mMap.animateCamera(CameraUpdateFactory.zoomTo(zoomvar));
        });

        // Set the click listener for the Zoom Out button.
        // Decrements the zoom level (down to a minimum of 2) and animates the camera zoom.
        Button minusZOOM = findViewById(R.id.zoomOUT);
        minusZOOM.setOnClickListener(v -> {
            zoomvar = Math.max(2, zoomvar - 1);
            mMap.animateCamera(CameraUpdateFactory.zoomTo(zoomvar));
        });

        // Set the click listener for the Line button.
        // Draws a line between TU Sofia and UNSS when the button is clicked.
        Button line = findViewById(R.id.line);
        line.setOnClickListener(v -> drawLine(TUsofia, unss));

        // Set the click listener for the Polygon button.
        // Draws a polygon connecting multiple locations when the button is clicked.
        Button polygon = findViewById(R.id.polygon);
        polygon.setOnClickListener(v -> drawPolygon());

        // Set the click listener for the Back button.
        // Returns to MainActivity and closes this activity when the button is clicked.
        Button back = findViewById(R.id.BACK);
        back.setOnClickListener(v -> {
            Intent intent = new Intent(PoiActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    /**
     * Resizes a bitmap image resource to the specified dimensions.
     *
     * @param drawableResId The resource ID of the drawable image.
     * @param width         The target width.
     * @param height        The target height.
     * @return A BitmapDescriptor created from the resized bitmap.
     */
    private BitmapDescriptor resizeBitmap(int drawableResId, int width, int height) {
        Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(), drawableResId);
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, width, height, false);

        return BitmapDescriptorFactory.fromBitmap(resizedBitmap);
    }

    /**
     * Adds a marker to the map at a given location with a title and color.
     *
     * @param location The geographical coordinates for the marker.
     * @param title    The title displayed on the marker's info window.
     * @param color    The hue for the default marker icon.
     */
    private void addMarker(LatLng location, String title, float color) {
        MarkerOptions markerOptions = new MarkerOptions()
                .position(location)
                .title(title)
                .icon(BitmapDescriptorFactory.defaultMarker(color));

        Marker marker = mMap.addMarker(markerOptions);

        if (marker != null) marker.showInfoWindow();
    }

    /**
     * Moves the camera to a specified location with a preset zoom level.
     *
     * @param location The target location for the camera.
     */
    private void moveCamera(LatLng location) {
        if (mMap == null) {
            Log.e("PoiActivity", "GoogleMap is NULL in moveCamera()");
            return;
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));
    }

    /**
     * Draws a polyline between two locations with custom styling.
     *
     * @param start The starting point of the line.
     * @param end   The ending point of the line.
     */
    private void drawLine(LatLng start, LatLng end) {
        PolylineOptions plo = new PolylineOptions()
                .add(start)
                .add(end)
                .color(Color.YELLOW)
                .geodesic(true)
                .startCap(new RoundCap())
                .width(10)
                .jointType(JointType.BEVEL);

        mMap.addPolyline(plo);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(start));
    }

    /**
     * Draws a polygon connecting predefined locations with a specific stroke style.
     */
    private void drawPolygon() {
        PolygonOptions polygonOptions = new PolygonOptions()
                .add(TUsofia, unss, lty, nsa)
                .strokeJointType(JointType.ROUND)
                .strokeColor(Color.BLUE)
                .strokeWidth(10);

        mMap.addPolygon(polygonOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(unss));
    }
}