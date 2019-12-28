package com.dev.wedrive.controls;

import com.dev.wedrive.MapActivity;
import com.dev.wedrive.R;
import com.dev.wedrive.dialog.CreateNewDriverPoint;
import com.dev.wedrive.entity.ApiLocation;
import com.dev.wedrive.informs.InformMessageFragment;
import com.google.android.gms.maps.model.LatLng;

public class DriverControls implements ControlsInterface {

    private MapActivity activity;

    public DriverControls(MapActivity activity) {
        this.activity = activity;
    }

    public DriverControls init() {
        activity.setFragment(R.id.lftControls, new DriverRoutesFragment());
//        activity.setFragment(R.id.inform,
//                new InformMessageFragment()
//                        .setHeader("Вы находитель офлайн!")
//                        .setText("Давай с нами в онлайн.")
//        );

        return this;
    }

    /**
     * @param latLng
     */
    public void onMapLongClick(LatLng latLng) {
        new CreateNewDriverPoint(activity, new ApiLocation(latLng)).create().show();
    }

}
