package com.example.louis.tourismghana;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.GeoPoint;

import static android.support.design.R.styleable.View;

public class SiteMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private static String SITE_NAME = "SITE_NAME";
    private static String SITE_MAP_LAT = "SITE_MAP_LAT";
    private static String SITE_MAP_LONG = "SITE_MAP_LONG";

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        checkNetworkState();
    }

    private int checkNetworkState() {

        int network;
        ConnectivityManager connectivityManager = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null || !networkInfo.isAvailable() || !networkInfo.isConnected())
        {
            network = 0;
            Toast.makeText(this, "No Internet Access. Check WIFI or Data", Toast.LENGTH_LONG).show();
        }
        else
        {
            network = 1;
        }
        return network;
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //Intitializing the getIntent() method.
        Intent getSiteIntent = getIntent();

        //Getting the values received from FinalSitePageActivity
        String Name = getSiteIntent.getStringExtra(SITE_NAME);
        String Latitude = getSiteIntent.getStringExtra(SITE_MAP_LAT);
        String Longitude = getSiteIntent.getStringExtra(SITE_MAP_LONG);

        //Converting the String Map Coordinates to double
        double latitude = Double.parseDouble(Latitude);
        double longitude = Double.parseDouble(Longitude);
        latitude = latitude * 1000000;
        longitude = longitude * 1000000;
        // Add a marker with Site Name and move the camera
        //GeoPoint touristSite = new GeoPoint(latitude * 1E6, longitude * 1E6);
        LatLng touristSite = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(touristSite).title(Name)).showInfoWindow();
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        //Enable zom in zoom out controls
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(touristSite));

        //Automatically zoom in to site
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
    }
}
