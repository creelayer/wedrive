package com.dev.wedrive.controls;

import android.content.Intent;

import com.dev.wedrive.MapActivity;
import com.dev.wedrive.R;
import com.dev.wedrive.RouteListActivity;
import com.dev.wedrive.adapters.LocationAdapter;
import com.dev.wedrive.collection.LocationCollection;
import com.dev.wedrive.dialog.CreateDriverLocationDialog;
import com.dev.wedrive.entity.ApiLocation;
import com.dev.wedrive.loaders.LoaderLocationManager;
import com.dev.wedrive.loaders.RouteLoader;
import com.dev.wedrive.service.RouteService;
import com.dev.wedrive.sheet.RouteDriverSheet;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

public class DriverControls implements ControlsInterface {

    private MapActivity activity;
    private LoaderLocationManager loader;
    private RouteService routeService;

    public DriverControls(MapActivity activity, LoaderLocationManager loaderCollection) {

        this.activity = activity;
        this.loader = loaderCollection;
        this.routeService = new RouteService();
    }

    public ControlsInterface init() {
        createControls();
        createSheet();
        createLoader();
        return this;
    }

    public void onMapLongClick(LatLng latLng) {
        routeService.getCurrentRoute((route) -> {
            if (route == null)
                activity.startActivity(new Intent(activity, RouteListActivity.class));
            else
                new CreateDriverLocationDialog(activity, new ApiLocation(latLng, route)).create().show();

        });
    }

    public boolean onMarkerClick(Marker marker) {

        String uuid = marker.getTag().toString();
        RouteLoader routeLoader = (RouteLoader) loader.getLast();
        LocationCollection locationCollection = routeLoader.getLocationCollection();
        LocationAdapter locationAdapter = locationCollection.get(uuid);

        new CreateDriverLocationDialog(activity, locationAdapter.getLocation()).create().show();
        return true;
    }

    private void createControls() {
        activity.setFragment(R.id.lftControls, new DriverRoutesFragment());
    }

    private void createSheet() {
        routeService.getCurrentRoute((route) -> {
            if (route != null) {
                RouteDriverSheet sheet = new RouteDriverSheet();
                sheet.setRoute(route);
                activity.setFragment(R.id.btmControls, sheet);
            }
        });
    }

    private void createLoader() {
        routeService.getCurrentRoute((route) -> {
            if (route != null) {
                loader.clear();
                loader.add(new RouteLoader(route));
                loader.load();
            }
        });
    }

}
