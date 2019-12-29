package com.dev.wedrive.controls;

import android.content.Intent;

import com.dev.wedrive.MapActivity;
import com.dev.wedrive.R;
import com.dev.wedrive.RouteListActivity;
import com.dev.wedrive.dialog.CreateNewDriverLocationDialog;
import com.dev.wedrive.entity.ApiLocation;
import com.dev.wedrive.service.RouteService;
import com.google.android.gms.maps.model.LatLng;

public class DriverControls implements ControlsInterface {

    private MapActivity activity;
    private RouteService routeService;

    public DriverControls(MapActivity activity) {

        this.activity = activity;
        this.routeService = new RouteService();
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

        routeService.getCurrentRoute((route) -> {
            if (route == null) {
                Intent myIntent = new Intent(activity, RouteListActivity.class);
                activity.startActivity(myIntent);
            } else {
                new CreateNewDriverLocationDialog(activity, new ApiLocation(latLng, route)).create().show();
            }
            return null;
        });
    }

}
