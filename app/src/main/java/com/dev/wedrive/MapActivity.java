package com.dev.wedrive;

import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import com.dev.wedrive.controls.ControlsInterface;
import com.dev.wedrive.controls.DriverControls;
import com.dev.wedrive.loaders.LoaderCollection;
import com.dev.wedrive.service.MapService;
import com.dev.wedrive.service.ProfileService;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import lombok.Getter;
import lombok.Setter;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnMapLongClickListener {

    public final int MIN_TIME = 10000;
    public final int MIN_DISTANCE = 10;
    public final int ZOOM = 15;

    private boolean cameraInited = false;

    private LocationManager locationManager;

    private GoogleMap map;

    private ControlsInterface controller;

    @Getter
    private ProfileService profileService;

    @Getter
    private MapService mapService;

    @Setter
    @Getter
    private LoaderCollection loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        Button testBtn = findViewById(R.id.test_btn);
        testBtn.setOnClickListener((v) -> {
            Intent myIntent = new Intent(this, CarListActivity.class);
            startActivity(myIntent);
        });

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
    public void onMapReady(GoogleMap map) {

        this.map = map;
        mapService = new MapService(map);
        loader = new LoaderCollection(mapService);

        profileService = new ProfileService();
        profileService.getMyProfile((profile) -> {
            controller = new DriverControls(this, loader);
            controller.init();
            return null;
        }, (error) -> {
            return null;
        });


        map.setMyLocationEnabled(true);
        map.setOnMarkerClickListener(this);
        map.setOnMapLongClickListener(this);
    }

    public void setFragment(int id, Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(id, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    @Override
    protected void onResume() {
        super.onResume();
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DISTANCE, locationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, locationListener);

        if (controller != null) controller.init();
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(locationListener);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return controller.onMarkerClick(marker);
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        controller.onMapLongClick(latLng);
    }

    /**
     * Change location listner
     */
    private LocationListener locationListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {

            if (!cameraInited) {
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), ZOOM));
                cameraInited = true;
            }

            mapService.updateMyLocation(new LatLng(location.getLatitude(), location.getLongitude()));
            loader.run();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };
}
