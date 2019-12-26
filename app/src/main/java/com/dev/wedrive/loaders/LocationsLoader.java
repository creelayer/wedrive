package com.dev.wedrive.loaders;

import com.dev.wedrive.collection.LocationCollection;
import com.dev.wedrive.entity.ApiLocation;
import com.dev.wedrive.entity.MMarker;
import com.dev.wedrive.entity.MMarkerFactory;
import com.dev.wedrive.service.LocationService;
import com.dev.wedrive.service.MapService;
import com.google.android.gms.maps.GoogleMap;

public class LocationsLoader implements LoaderInterface {

    protected LocationCollection locationCollection;
    protected MapService mapService;
    protected LocationService locationService;

    public LocationsLoader() {
        locationCollection = new LocationCollection();
        locationService = new LocationService();
    }

    @Override
    public void setMapService(MapService mapService) {
        this.mapService = mapService;
    }

    @Override
    public void load() {
        run();
    }

    @Override
    public void run() {
        locationService.getNearestLocations((locations) -> {
            mapService.updateLocations(locationCollection.put(locations));
            return null;
        });
    }
}
