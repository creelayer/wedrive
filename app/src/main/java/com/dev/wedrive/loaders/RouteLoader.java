package com.dev.wedrive.loaders;

import com.dev.wedrive.entity.ApiLocation;
import com.dev.wedrive.entity.ApiRoute;
import com.dev.wedrive.entity.MMarker;
import com.dev.wedrive.entity.MMarkerFactory;
import com.google.android.gms.maps.GoogleMap;

public class RouteLoader extends LoaderAbstract {

    protected ApiRoute route;

    public RouteLoader(ApiRoute route) {
        this.route = route;
    }

    @Override
    public void load() {
        run();
    }

    @Override
    public void run() {
        locationService.getLocationsByRoute(route, (locations) -> {

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
