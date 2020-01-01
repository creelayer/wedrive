package com.dev.wedrive.service;

import com.dev.wedrive.R;
import com.dev.wedrive.adapters.LocationAdapter;
import com.dev.wedrive.collection.LocationCollection;
import com.dev.wedrive.entity.ApiLocation;
import com.dev.wedrive.entity.TypeInterface;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.Map;

public class MapService {

    protected GoogleMap map;
    private LocationService locationService;

    public MapService(GoogleMap map) {
        this.map = map;
        this.locationService = new LocationService();
    }

    public void updateMyLocation(LatLng latLng) {
        locationService.updateMyLocation(latLng);
    }

    public void updateLocations(LocationCollection locations) {

        Map<String, LocationAdapter> delete = new HashMap<>();

        for (Map.Entry<String, LocationAdapter> entry : locations.entrySet()) {

            LocationAdapter locationAdapter = entry.getValue();

            if (locationAdapter.getMarker() == null)
                locationAdapter.setMarker(createMarker(locationAdapter.getLocation()));


            if (locationAdapter.getUpdated() < locations.getUpdated())
                delete.put(locationAdapter.getUuid(), locationAdapter);


        }

        for (Map.Entry<String, LocationAdapter> entry : delete.entrySet())
            if (entry.getValue().getUpdated() < locations.getUpdated())
                locations.remove(entry.getValue());

        locations.touch();
    }

    public void clearLocations() {
        map.clear();
    }

    private Marker createMarker(ApiLocation location) {

        int icon = R.drawable.ic_car;

        switch (location.getType()) {
            case TypeInterface.TYPE_DRIVER:
                icon = R.drawable.ic_car;
                break;
            case TypeInterface.TYPE_PASSENGER:
                icon = R.drawable.ic_man;
                break;
            case TypeInterface.TYPE_DRIVER_LOCATION:
                icon = R.drawable.ic_driver_location;
                break;
            case TypeInterface.TYPE_PASSENGER_LOCATION:
                icon = R.drawable.ic_passenger_location;
                break;
        }

        return map.addMarker(
                new MarkerOptions()
                        .position(location.getLatLng())
                        .title(location.getType())
                        .icon(BitmapDescriptorFactory.fromResource(icon)));
    }

}
