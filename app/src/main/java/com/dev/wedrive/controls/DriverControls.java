package com.dev.wedrive.controls;

import com.dev.wedrive.MapActivity;
import com.dev.wedrive.R;

public class DriverControls implements ControlsInterface {

    private MapActivity activity;

    public DriverControls(MapActivity activity) {
        this.activity = activity;
    }

    public void init() {

        DriverRoutesFragment fragment = new DriverRoutesFragment();
        fragment.setActivity(activity);

        activity.getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.lftControls, fragment)
                .commit();
    }


}
