package com.dev.wedrive.loaders;

import com.dev.wedrive.entity.ApiLocation;
import com.dev.wedrive.entity.MMarker;
import com.dev.wedrive.entity.MMarkerFactory;
import com.google.android.gms.maps.GoogleMap;

public class DefaultLoader extends LoaderAbstract {

    public DefaultLoader(GoogleMap map) {
        super();
    }

    @Override
    public void load() {
        run();
    }

    @Override
    public void run() {
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
}
