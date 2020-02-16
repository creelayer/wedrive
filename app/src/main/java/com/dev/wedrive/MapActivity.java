package com.dev.wedrive;

import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.dev.wedrive.controls.ControlsFactory;
import com.dev.wedrive.controls.ControlsInterface;
import com.dev.wedrive.helpers.FileHelper;
import com.dev.wedrive.informs.InformManager;
import com.dev.wedrive.loaders.LoaderLocationManager;
import com.dev.wedrive.service.ApiService;
import com.dev.wedrive.service.MapService;
import com.dev.wedrive.service.ProfileService;
import com.dev.wedrive.util.ProfileImageUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.material.navigation.NavigationView;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

public class MapActivity extends AbstractAuthActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnMapLongClickListener, NavigationView.OnNavigationItemSelectedListener {

    public final int MIN_TIME = 10000;
    public final int MIN_DISTANCE = 10;
    public final int ZOOM = 15;

    private boolean cameraInit = false;

    private LocationManager locationManager;

    private GoogleMap map;

    private ControlsInterface controller;

    @Getter
    private ProfileService profileService;

    @Getter
    private MapService mapService;

    @NonNull
    @Getter
    private InformManager informManager;

    @Setter
    @Getter
    private LoaderLocationManager loaderLocationManager;

    private ImageView navImage;
    private TextView navName;
    private TextView navType;

    public MapActivity() {
        super();
        informManager = new InformManager(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(this);

        navImage = headerView.findViewById(R.id.nav_image);
        navName = headerView.findViewById(R.id.nav_name);
        navType = headerView.findViewById(R.id.nav_type);

        Button testBtn = findViewById(R.id.test_btn);
        // testBtn.setOnClickListener((v) -> startActivity(new Intent(this, RequestListActivity.class)));

        testBtn.setOnClickListener((v) -> ApiService.getInstance().setToken(null));

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
        loaderLocationManager = new LoaderLocationManager(mapService);

        profileService = new ProfileService();
        profileService.getMyProfile((profile) -> {

            controller = ControlsFactory.create(this, profile, loaderLocationManager).init();
            loaderLocationManager.start();

            navName.setText(profile.name + " " + profile.lastName);
            navType.setText(profile.type);

            if (profile.image != null)
                ProfileImageUtil
                        .get()
                        .load(Constants.API_URL + "/uploads/profile/" + FileHelper.getStyleName(profile.image, "sm"))
                        .into(navImage);

        }, (error) -> {
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

        informManager.start();
        if (loaderLocationManager != null)
            loaderLocationManager.start();

        if (controller != null)
            controller.init();
    }

    @Override
    protected void onPause() {
        super.onPause();
        informManager.stop();
        if (loaderLocationManager != null)
            loaderLocationManager.stop();

        locationManager.removeUpdates(locationListener);
    }

    @Override
    public void onBackPressed() {
        return;
    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        return controller.onMarkerClick(marker);
    }


    @Override
    public void onMapLongClick(LatLng latLng) {
        controller.onMapLongClick(latLng);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_profile_edit)
            goTo(ProfileEditActivity.class);

        if (id == R.id.nav_log_out)
            ApiService.getInstance().setToken(null);

        return true;
    }

    /**
     * Change location listner
     */
    private LocationListener locationListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {

            if (!cameraInit) {
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), ZOOM));
                cameraInit = true;
            }

            mapService.updateMyLocation(new LatLng(location.getLatitude(), location.getLongitude()));
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


    public void startProfileActivity() {

    }
}
