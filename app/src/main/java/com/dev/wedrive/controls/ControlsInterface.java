package com.dev.wedrive.controls;

import com.google.android.gms.maps.model.LatLng;

public interface ControlsInterface {

    public void init();

    public void createControls();

    public void createSheet();

    public void createLoader();

    public void onMapLongClick(LatLng latLng);
}
