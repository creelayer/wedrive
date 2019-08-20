package com.dev.wedrive.controller;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.dev.wedrive.MapActivity;
import com.dev.wedrive.R;
import com.dev.wedrive.dialogs.DeleteRouteDialog;
import com.dev.wedrive.dialogs.DriverLocationDialog;
import com.dev.wedrive.dialogs.RouteDialog;
import com.dev.wedrive.dialogs.RouteInfoDialog;
import com.dev.wedrive.entity.ApiLocation;
import com.dev.wedrive.entity.ApiRoute;
import com.dev.wedrive.entity.MMarker;
import com.dev.wedrive.service.MapService;
import com.dev.wedrive.service.RouteService;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

public class DriverController implements View.OnClickListener, View.OnLongClickListener, ControllerInterface {

    private MapActivity mapActivity;
    private LinearLayout routeLayout;

    private MapService mapService;
    private RouteService routeService;
    private ApiRoute currentRoute;

    private int btnN = 0;

    public DriverController(MapActivity mapActivity) {
        this.mapActivity = mapActivity;
        this.mapService = mapActivity.getMapService();
        this.routeService = new RouteService();
        routeLayout = (LinearLayout) mapActivity.findViewById(R.id.route_layout);
        createRouteControls();
    }

    /**
     * Add route buttons on map interface
     */
    private void createRouteControls() {

        Button addBtn = new Button(mapActivity);
        addBtn.setText("+");
        addBtn.setId(R.id.add_route_btn);
        addBtn.setOnClickListener(this);
        routeLayout.addView(addBtn);

        btnN = 0;
        routeService.getMyRouts(ApiRoute.TYPE_DRIVER, (routes) -> {
            for (ApiRoute route : routes) {
                if (currentRoute == null) {
                    showRoute(route);
                }

                btnN++;
                Button btn = new Button(mapActivity);
                btn.setText(String.valueOf(btnN));
                btn.setTag(route);
                btn.setOnClickListener(this);
                btn.setOnLongClickListener(this);
                routeLayout.addView(btn);
            }

            return null;
        });
    }

    /**
     * Update route buttons on map interface
     */
    public void updateRouteControls(ApiRoute route) {
        routeLayout.removeAllViews();
        createRouteControls();
        showRoute(route);
    }

    /**
     *
     */
    private void showRoute(ApiRoute route) {

        currentRoute = route;

        if (route == null) {
            mapService.clearRouteLocations();
            return;
        }

        mapService.loadLocationsByRoute(route);
    }


    /**
     * Load routs and animate popup
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.add_route_btn) {
            new RouteDialog(mapActivity).show();
        } else if (v.getId() == R.id.edit_route_btn) {
            new RouteDialog(mapActivity, currentRoute).show();
        } else {
            showRoute((ApiRoute) v.getTag());
            new RouteInfoDialog(mapActivity, currentRoute).show();
        }
    }

    @Override
    public boolean onLongClick(View v) {
        ApiRoute route = (ApiRoute) v.getTag();
        new DeleteRouteDialog(mapActivity, route).show();
        return false;
    }

    /**
     * @param marker
     */
    public void onMarkerClick(Marker marker) {

        MMarker mMarker = mapService.getMMarker(marker);

        switch (mMarker.getType()) {
            case ApiLocation.TYPE_DRIVER_LOCATION :
                new DriverLocationDialog(mapActivity, (ApiLocation) mMarker.getLocation()).show();
                break;
        }

    }

    /**
     * @param latLng
     */
    public void onMapLongClick(LatLng latLng) {
        ApiLocation location = new ApiLocation(latLng, currentRoute);
        new DriverLocationDialog(mapActivity, location).show();
    }


}
