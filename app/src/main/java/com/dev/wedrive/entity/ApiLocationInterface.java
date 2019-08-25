package com.dev.wedrive.entity;

import com.google.android.gms.maps.model.LatLng;

public interface ApiLocationInterface {

    public String getUuid();

    public String getType();

    public LatLng getLatLng();

    public double getLatitude();

    public double getLongitude();

    //public DriverLocationData getData();
}
