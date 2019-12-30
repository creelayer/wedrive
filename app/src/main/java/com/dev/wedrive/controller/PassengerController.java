package com.dev.wedrive.controller;

import com.dev.wedrive.MapActivity;
import com.dev.wedrive.entity.ApiRoute;
import com.dev.wedrive.service.LocationService;
import com.dev.wedrive.service.RouteService;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

public class PassengerController implements ControllerInterface {

    private MapActivity mapActivity;

    private RouteService routeService;
    private LocationService locationService;
    private ApiRoute currentRoute;

    public PassengerController(MapActivity mapActivity) {
        this.mapActivity = mapActivity;
        this.routeService = new RouteService();
        this.locationService = new LocationService();
        createRouteControls();
    }

    /**
     * Add route buttons on map interface
     */
    private void createRouteControls() {
        routeService.getMyRouts(ApiRoute.TYPE_PASSENGER, (routes) -> {

//            if (!routes.isEmpty()) {
//                currentRoute = routes.get(0);
//            } else {
//                routeService.createRoute(ApiRoute.DEFAULT_NAME, ApiRoute.TYPE_PASSENGER, (route) -> {
//                    currentRoute = route;
//                    return null;
//                });
//            }

            return null;
        });
    }

    /**
     * @param marker
     */
    public void onMarkerClick(Marker marker) {
//        MMarker mMarker = mapActivity.getLoader().getLast().getMarkerCollection().get(marker);
//
//        locationService.getLocationInfo((ApiLocation) mMarker.getLocation(), (location) -> {
//            switch (mMarker.getType()) {
//                case MMarker.TYPE_DRIVER:
//                case MMarker.TYPE_DRIVER_LOCATION:
//                    new RouteInfoDialog(mapActivity, location).show();
//                    if (!(mapActivity.getLoader().getLast() instanceof RouteLoader)) {
//                        mapActivity.getLoader().add(new RouteLoader(location.route));
//                    }
//                    break;
//            }
//            return null;
//        });


    }

    /**
     * @param latLng
     */
    public void onMapLongClick(LatLng latLng) {
//        ApiLocation location = new ApiLocation(latLng, currentRoute);
//        new LocationPassengerEditDialog(mapActivity, location).show();
    }

}
