package com.dev.wedrive.service;

import com.dev.wedrive.collection.MarkerCollection;
import com.dev.wedrive.entity.ApiLocation;
import com.dev.wedrive.entity.ApiRoute;
import com.dev.wedrive.entity.MMarker;
import com.dev.wedrive.entity.MMarkerFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;


public class MapService {

    private GoogleMap map;
    private MarkerCollection markerCollection;
    private LocationService locationService;

    /**
     * @param map
     */
    public MapService(GoogleMap map) {
        this.map = map;
        this.markerCollection = new MarkerCollection();
        this.locationService = new LocationService();
    }

    /**
     * @param marker
     * @return
     */
    public MMarker getMMarker(Marker marker) {
        return markerCollection.get(marker);
    }

    /**
     * @param group
     */
    public void removeByGroup(String group) {
        markerCollection.removeByGroup(group);
    }

    /**
     *
     */
    public void clearRouteLocations() {
        removeByGroup(MMarker.GROUP_ROUTE);
    }

    /**
     * @param latLng
     */
    public void updateMyLocation(LatLng latLng) {
        locationService.updateMyLocation(latLng);
    }

    /**
     * Update all markers on map
     */
    public void loadNearestLocations() {
        locationService.getNearestLocations((locations) -> {

            for (ApiLocation location : locations) {

                MMarker marker = markerCollection.get(location);

                if (marker == null) {
                    markerCollection.add(MMarkerFactory.make(map, location));
                } else {
                    marker.setLocation(location);
                }
            }

            return null;
        });
    }

    /**
     * @param route
     */
    public void loadLocationsByRoute(ApiRoute route) {

        clearRouteLocations();

        locationService.getRouteLocations(route, (locations) -> {

            for (ApiLocation location : locations) {
                MMarker marker = markerCollection.get(location.uuid);

                if (marker == null) {
                    markerCollection.add(MMarkerFactory.make(map, location).setGroup(MMarker.GROUP_ROUTE));
                } else {
                    marker.setLocation(location);
                }
            }

            return null;
        });
    }


}
