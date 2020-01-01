package com.dev.wedrive.controls;

import com.dev.wedrive.MapActivity;
import com.dev.wedrive.adapters.LocationAdapter;
import com.dev.wedrive.collection.LocationCollection;
import com.dev.wedrive.entity.ApiLocation;
import com.dev.wedrive.entity.ApiProfile;
import com.dev.wedrive.loaders.LoaderCollection;
import com.dev.wedrive.loaders.NearestLoader;
import com.dev.wedrive.service.LocationService;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

public class PassengerControls implements ControlsInterface {

    private MapActivity activity;
    private LoaderCollection loader;
    private LocationService locationService;

    public PassengerControls(MapActivity activity, LoaderCollection loaderCollection) {

        this.activity = activity;
        this.loader = loaderCollection;

        locationService = new LocationService();
    }

    public ControlsInterface init() {
        createLoader();
        return this;
    }

    public void onMapLongClick(LatLng latLng) {

    }

    public boolean onMarkerClick(Marker marker) {

        String uuid = marker.getTag().toString();
        NearestLoader nearestLoader = (NearestLoader) loader.getLast();
        LocationCollection locationCollection = nearestLoader.getLocationCollection();
        LocationAdapter locationAdapter = locationCollection.get(uuid);

        locationService.getLocationInfo(locationAdapter.getLocation(),(location) ->{
            ApiLocation l = location;

            return null;

        });


        return true;
    }

    private void createLoader() {
        loader.clear();
        loader.add(new NearestLoader(ApiProfile.TYPE_DRIVER));
        loader.load();
    }
}
