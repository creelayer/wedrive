package com.dev.wedrive;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.dev.wedrive.controller.ControllerFactory;
import com.dev.wedrive.controller.ControllerInterface;
import com.dev.wedrive.entity.ApiCurrentLocation;
import com.dev.wedrive.entity.ApiLocation;
import com.dev.wedrive.entity.ApiLocationInterface;
import com.dev.wedrive.entity.ApiProfile;
import com.dev.wedrive.service.MapService;
import com.dev.wedrive.service.ProfileService;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import lombok.Getter;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnMapLongClickListener {

    public final int MIN_TIME = 10000;
    public final int MIN_DISTANCE = 10;
    public final int ZOOM = 14;

    private LocationManager locationManager;


    private GoogleMap map;

    @Getter
    private ControllerInterface controller;

    @Getter
    private ProfileService profileService;

    @Getter
    private MapService mapService;

    private ApiProfile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
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
        this.profileService = new ProfileService();
        this.mapService = new MapService(map);

        profileService.getMyProfile((profile) -> {
            this.profile = profile;
            this.controller = ControllerFactory.make(profile, this, this.mapService);
            return null;
        }, (error) -> {
            return null;
        });


        map.setMyLocationEnabled(true);
        map.setOnMarkerClickListener(this);
        map.setOnMapLongClickListener(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DISTANCE, locationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, locationListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(locationListener);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        controller.onMarkerClick(marker);
        return false;
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        controller.onMapLongClick(latLng);
    }

    /**
     * Change location listner
     */
    private LocationListener locationListener = new LocationListener() {

        private boolean inited = false;

        @Override
        public void onLocationChanged(Location location) {

            if(!this.inited){
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), ZOOM));
                this.inited = true;
            }

            mapService.updateMyLocation(new LatLng(location.getLatitude(), location.getLongitude()));
            mapService.loadNearestCurrentLocations(profile.getType().equals(ApiCurrentLocation.TYPE_DRIVER) ? ApiCurrentLocation.TYPE_PASSENGER : ApiCurrentLocation.TYPE_DRIVER);
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
