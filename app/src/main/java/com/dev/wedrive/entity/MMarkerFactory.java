package com.dev.wedrive.entity;

import com.dev.wedrive.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MMarkerFactory {

    public static MMarker make(GoogleMap map, ApiLocationInterface location) {
        Marker marker = map.addMarker(
                new MarkerOptions()
                        .position(location.getLatLng())
                        .title(location.getType())
                        .icon(BitmapDescriptorFactory.fromResource(location.getType().equals(ApiProfile.TYPE_DRIVER) ? R.drawable.ic_car : R.drawable.ic_man)));
        marker.setTag(location.getUuid());
        return new MMarker(location, marker);
    }


}
