package com.dev.wedrive.controls;

import com.dev.wedrive.MapActivity;
import com.dev.wedrive.R;
import com.dev.wedrive.adapters.LocationAdapter;
import com.dev.wedrive.collection.LocationCollection;
import com.dev.wedrive.dialog.CreateDriverLocationDialog;
import com.dev.wedrive.dialog.CreatePassengerLocationDialog;
import com.dev.wedrive.dialog.InformDialog;
import com.dev.wedrive.entity.ApiLocation;
import com.dev.wedrive.entity.ApiProfile;
import com.dev.wedrive.entity.TypeInterface;
import com.dev.wedrive.loaders.ActiveLocationsLoader;
import com.dev.wedrive.loaders.LoaderLocationManager;
import com.dev.wedrive.loaders.NearestLoader;
import com.dev.wedrive.service.LocationService;
import com.dev.wedrive.service.RouteService;
import com.dev.wedrive.sheet.RouteSheet;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

public class PassengerControls implements ControlsInterface {

    private MapActivity activity;
    private LoaderLocationManager loader;
    private LocationService locationService;

    public PassengerControls(MapActivity activity, LoaderLocationManager loaderCollection) {

        this.activity = activity;
        this.loader = loaderCollection;

        locationService = new LocationService();
    }

    public ControlsInterface init() {
        createLoader();
        return this;
    }

    public void onMapLongClick(LatLng latLng) {
        locationService.getActivePassengerLocation((location) -> {
            if (location != null)
                new InformDialog(activity).setHeaderText("Inform").setMessageText("You already have location. Please delete active location to add else.").create().show();
            else
                new CreatePassengerLocationDialog(activity, new ApiLocation(latLng, TypeInterface.TYPE_PASSENGER_LOCATION)).create().show();
        });
    }

    /**
     * Get passenger active locations.
     * Active locations is my own created location(passenger may create only one location) or drivers active route locations
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

        if (location.type.equals(TypeInterface.TYPE_PASSENGER_LOCATION))
            new CreatePassengerLocationDialog(activity, location).create().show();
        else
            createSheet(location);

        return true;
    }

    private void createSheet(ApiLocation location) {
        RouteSheet sheet = new RouteSheet();
        sheet.setLocation(location);
        sheet.expand();
        activity.setFragment(R.id.btmControls, sheet);
    }

    private void createLoader() {
        loader.reset(new ActiveLocationsLoader());
    }
}
