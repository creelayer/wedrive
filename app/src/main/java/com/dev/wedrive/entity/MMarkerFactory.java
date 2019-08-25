package com.dev.wedrive.entity;

import com.dev.wedrive.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MMarkerFactory {

    public static MMarker make(GoogleMap map, ApiLocation location) {

        int icon = 0;

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
            default:
                icon = R.drawable.ic_car;
                break;
        }


        Marker marker = map.addMarker(
                new MarkerOptions()
                        .position(location.getLatLng())
                        .title(location.getType())
                        .icon(BitmapDescriptorFactory.fromResource(icon)));
        marker.setTag(location.getUuid());
        return new MMarker(location, marker);
    }


}
