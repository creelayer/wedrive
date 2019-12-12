package com.dev.wedrive.controller;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.dev.wedrive.MapActivity;
import com.dev.wedrive.R;
import com.dev.wedrive.dialogs.LocationDriverEditDialog;
import com.dev.wedrive.entity.ApiLocation;
import com.dev.wedrive.entity.ApiRoute;
import com.dev.wedrive.entity.MMarker;
import com.dev.wedrive.loaders.LoaderCollection;
import com.dev.wedrive.loaders.RouteLoader;
import com.dev.wedrive.service.LocationService;
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
    private LocationService locationService;

    private int btnN = 0;

    public DriverController(MapActivity mapActivity) {
        this.mapActivity = mapActivity;
        this.mapService = mapActivity.getMapService();
        this.routeService = new RouteService();
        routeLayout = (LinearLayout) mapActivity.findViewById(R.id.route_layout);
        this.locationService = new LocationService();
        createRouteControls();
    }

    /**
     * Add route buttons on map interface
     */
    private void createRouteControls() {

        Button addBtn = new Button(mapActivity);
        addBtn.setText("+");
        addBtn.setId(R.id.route_add_btn);
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

        LoaderCollection loader = mapActivity.getLoader();

        if (!loader.isEmpty() && loader.getLast() instanceof RouteLoader) {
            mapActivity.getLoader().pop();
        }

        loader.add(new RouteLoader(route));
    }


    /**
     * Load routs and animate popup
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.route_add_btn) {

        } else if (v.getId() == R.id.edit_route_btn) {

        } else {
            showRoute((ApiRoute) v.getTag());
            //new RouteInfoDialog(mapActivity, currentRoute).show();
        }
    }

    @Override
    public boolean onLongClick(View v) {
        ApiRoute route = (ApiRoute) v.getTag();
        return false;
    }

    /**
     * @param marker
     */
    public void onMarkerClick(Marker marker) {

        MMarker mMarker = mapActivity.getLoader().getLast().getMarkerCollection().get(marker);

        locationService.getLocationInfo((ApiLocation) mMarker.getLocation(), (location) -> {
            switch (mMarker.getType()) {
                case MMarker.TYPE_DRIVER:
                case MMarker.TYPE_DRIVER_LOCATION:
                    new LocationDriverEditDialog(mapActivity, location).show();
                    if (!(mapActivity.getLoader().getLast() instanceof RouteLoader)) {
                        mapActivity.getLoader().add(new RouteLoader(location.route));
                    }
                    break;
            }
            return null;
        });

    }

    /**
     * @param latLng
     */
    public void onMapLongClick(LatLng latLng) {
        ApiLocation location = new ApiLocation(latLng, currentRoute);
        new LocationDriverEditDialog(mapActivity, location).show();
    }

}
