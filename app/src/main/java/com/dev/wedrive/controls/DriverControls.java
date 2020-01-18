package com.dev.wedrive.controls;

import android.content.Intent;

import com.dev.wedrive.CarListActivity;
import com.dev.wedrive.MapActivity;
import com.dev.wedrive.R;
import com.dev.wedrive.RouteListActivity;
import com.dev.wedrive.adapters.LocationAdapter;
import com.dev.wedrive.collection.LocationCollection;
import com.dev.wedrive.dialog.CreateDriverLocationDialog;
import com.dev.wedrive.dialog.CreatePassengerLocationDialog;
import com.dev.wedrive.dialog.InformDialog;
import com.dev.wedrive.entity.ApiLocation;
import com.dev.wedrive.entity.ApiRoute;
import com.dev.wedrive.entity.TypeInterface;
import com.dev.wedrive.loaders.ActiveLocationsLoader;
import com.dev.wedrive.loaders.LoaderLocationManager;
import com.dev.wedrive.loaders.RouteLoader;
import com.dev.wedrive.service.RouteService;
import com.dev.wedrive.sheet.PassengerSheet;
import com.dev.wedrive.sheet.RouteSheet;
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
        routeService.getCurrentRoute((route) -> {
            if (route == null)
                activity.startActivity(new Intent(activity, RouteListActivity.class));
            else if (route.car == null)
                activity.startActivity(new Intent(activity, CarListActivity.class));
            else {
                createControls();
                createSheet(route);
                createLoader(route);
            }
        });
        return this;
    }

    public void onMapLongClick(LatLng latLng) {
        routeService.getCurrentRoute((route) -> {
            if (route.status.equals(ApiRoute.STATUS_CURRENT) || route.status.equals(ApiRoute.STATUS_ACTIVE))
                new CreateDriverLocationDialog(activity, new ApiLocation(latLng, route)).create().show();
        });
    }

    /**
     * Get driver active locations.
     * Active locations is locations of my active route or passengers active locations
     *
     * @param marker
     * @return
     */
    public boolean onMarkerClick(Marker marker) {


        String uuid = marker.getTag().toString();
        ActiveLocationsLoader activeLocationsLoader = (ActiveLocationsLoader) loader.getLast();
        LocationCollection locationCollection = activeLocationsLoader.getLocationCollection();
        LocationAdapter locationAdapter = locationCollection.get(uuid);
        ApiLocation location = locationAdapter.getLocation();

        activeLocationsLoader.setActiveLocation(location).highlight();

        createSheet(location);

        return true;
    }

    private void createControls() {
        activity.setFragment(R.id.lftControls, new DriverRoutesFragment());
    }

    private void createSheet(ApiLocation location) {

        if (location.type.equals(TypeInterface.TYPE_DRIVER_LOCATION)) {
            RouteSheet sheet = new RouteSheet();
            sheet.setLocation(location);
            activity.setFragment(R.id.btmControls, sheet);
        } else {
            PassengerSheet sheet = new PassengerSheet();
            sheet.setLocation(location);
            activity.setFragment(R.id.btmControls, sheet);
        }


    }

    private void createSheet(ApiRoute route) {
        RouteSheet sheet = new RouteSheet();
        sheet.setRoute(route);
        activity.setFragment(R.id.btmControls, sheet);
    }

    private void createLoader(ApiRoute route) {

        if (route == null)
            return;

        loader.reset(new ActiveLocationsLoader());
    }

}
