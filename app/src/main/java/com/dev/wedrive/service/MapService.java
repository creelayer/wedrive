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

import java.util.Set;

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

    public Void updateLocations(LocationCollection locations) {


        Set<String> keys = locations.keySet();

        for (String key : keys){

            LocationAdapter locationAdapter = locations.get(key);
            ApiLocation location = locationAdapter.getLocation();
            Marker marker = locationAdapter.getMarker();

            if (marker == null) {

                int icon = 0;

                switch (locationAdapter.getLocation().getType()) {
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
                    default:
                        icon = R.drawable.ic_car;
                        break;
                }

                marker = map.addMarker(
                        new MarkerOptions()
                                .position(location.getLatLng())
                                .title(location.getType())
                                .icon(BitmapDescriptorFactory.fromResource(icon)));

                marker.setTag(location.getUuid());
                locationAdapter.setMarker(marker);
                locations.put(locationAdapter);

            } else {
                marker.setPosition(location.getLatLng());
            }

            if (locationAdapter.getUpdated() < locations.getUpdated()) {
                locations.remove(locationAdapter);
            }
        }
        locations.touch();
        return null;
    }

    public void clearLocations(){
        map.clear();
    }

}
