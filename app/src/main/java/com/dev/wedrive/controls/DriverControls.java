package com.dev.wedrive.controls;

import android.content.Intent;

import com.dev.wedrive.MapActivity;
import com.dev.wedrive.R;
import com.dev.wedrive.RouteListActivity;
import com.dev.wedrive.adapters.LocationAdapter;
import com.dev.wedrive.collection.LocationCollection;
import com.dev.wedrive.dialog.CreateDriverLocationDialog;
import com.dev.wedrive.entity.ApiLocation;
import com.dev.wedrive.loaders.LoaderCollection;
import com.dev.wedrive.loaders.RouteLoader;
import com.dev.wedrive.service.RouteService;
import com.dev.wedrive.sheet.RouteSheet;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

public class DriverControls implements ControlsInterface {

    private MapActivity activity;
    private LoaderCollection loader;
    private RouteService routeService;

    public DriverControls(MapActivity activity, LoaderCollection loaderCollection) {

        this.activity = activity;
        this.loader = loaderCollection;
        this.routeService = new RouteService();
    }

    public void init() {
        createControls();
        createSheet();
        createLoader();
    }

    public void createControls() {
        activity.setFragment(R.id.lftControls, new DriverRoutesFragment());
    }

    public void createSheet() {
        routeService.getCurrentRoute((route) -> {
            if (route != null) {
                RouteSheet sheet = new RouteSheet();
                sheet.setRoute(route);
                activity.setFragment(R.id.btmControls, sheet);
            }
            return null;
        });
    }

    public void createLoader() {
        routeService.getCurrentRoute((route) -> {
            if (route != null) {
                loader.clear();
                loader.add(new RouteLoader(route));
                loader.load();
            }
            return null;
        });
    }

    public void onMapLongClick(LatLng latLng) {
        routeService.getCurrentRoute((route) -> {
            if (route == null) {
                Intent myIntent = new Intent(activity, RouteListActivity.class);
                activity.startActivity(myIntent);
            } else {
                new CreateDriverLocationDialog(activity, new ApiLocation(latLng, route)).create().show();
            }
            return null;
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

}
