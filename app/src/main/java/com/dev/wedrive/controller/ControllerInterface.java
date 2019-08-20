package com.dev.wedrive.controller;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

public interface ControllerInterface {

    /**
     * @param marker
     */
    public void onMarkerClick(Marker marker);

    /**
     * @param latLng
     */
    public void onMapLongClick(LatLng latLng);

}
