package com.dev.wedrive.controller;

import com.dev.wedrive.MapActivity;
import com.dev.wedrive.dialogs.PassengerLocationDialog;
import com.dev.wedrive.entity.ApiLocation;
import com.dev.wedrive.entity.ApiRoute;
import com.dev.wedrive.entity.MMarker;
import com.dev.wedrive.service.MapService;
import com.dev.wedrive.service.RouteService;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

public class PassengerController implements ControllerInterface {

    private MapActivity mapActivity;

    private MapService mapService;
    private RouteService routeService;
    private ApiRoute currentRoute;


    public PassengerController(MapActivity mapActivity) {
        this.mapActivity = mapActivity;
        this.mapService = mapActivity.getMapService();
        this.routeService = new RouteService();
        createRouteControls();
    }

    /**
     * Add route buttons on map interface
     */
    private void createRouteControls() {
        routeService.getMyRouts(ApiRoute.TYPE_PASSENGER, (routes) -> {

            if (!routes.isEmpty()) {
                currentRoute = routes.get(0);
            } else {
                routeService.createRoute(ApiRoute.DEFAULT_NAME, ApiRoute.TYPE_PASSENGER, (route) -> {
                    currentRoute = route;
                    return null;
                });
            }

            return null;
        });
    }

    /**
     * @param marker
     */
    public void onMarkerClick(Marker marker) {
        MMarker mMarker = mapService.getMMarker(marker);

        switch (mMarker.getType()) {
            case MMarker.TYPE_PASSENGER_LOCATION:
                new PassengerLocationDialog(mapActivity, (ApiLocation) mMarker.getLocation()).show();
                break;
        }
    }

    /**
     * @param latLng
     */
    public void onMapLongClick(LatLng latLng) {
//        ApiLocation location = new ApiLocation(latLng, currentRoute);
//        new PassengerLocationDialog(mapActivity, location).show();
    }

}
