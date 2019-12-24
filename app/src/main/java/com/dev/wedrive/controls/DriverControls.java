package com.dev.wedrive.controls;

import com.dev.wedrive.MapActivity;
import com.dev.wedrive.R;

public class DriverControls implements ControlsInterface {

    private MapActivity activity;

    public DriverControls(MapActivity activity) {
        this.activity = activity;
    }

    public void init() {
        activity.setFragment(R.id.lftControls, new DriverRoutesFragment());
    }

}
