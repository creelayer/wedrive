package com.dev.wedrive.service;

import com.dev.wedrive.collection.MarkerCollection;
import com.dev.wedrive.entity.MMarker;
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
     * @param latLng
     */
    public void updateMyLocation(LatLng latLng) {
        locationService.updateMyLocation(latLng);
    }

}
