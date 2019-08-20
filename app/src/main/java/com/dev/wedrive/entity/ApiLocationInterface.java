package com.dev.wedrive.entity;

import com.google.android.gms.maps.model.LatLng;

public interface ApiLocationInterface {

    public final static String TYPE_DRIVER = "driver";
    public final static String TYPE_PASSENGER = "passenger";

    public String getUuid();

    public String getType();

    public LatLng getLatLng();

    public double getLatitude();

    public double getLongitude();
}
