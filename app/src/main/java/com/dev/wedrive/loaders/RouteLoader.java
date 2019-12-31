package com.dev.wedrive.loaders;

import androidx.arch.core.util.Function;

import com.dev.wedrive.collection.LocationCollection;
import com.dev.wedrive.entity.ApiRoute;
import com.dev.wedrive.service.LocationService;

import lombok.Getter;

public class RouteLoader implements LoaderInterface {

    @Getter
    protected LocationCollection locationCollection;
    protected LocationService locationService;

    protected ApiRoute route;

    public RouteLoader(ApiRoute route) {
        locationCollection = new LocationCollection();
        locationService = new LocationService();
        this.route = route;
    }

    public void load(final Function<LocationCollection, Void> func) {
        run(func);
    }

    public void run(final Function<LocationCollection, Void> func) {
        locationService.getLocationsByRoute(route, (locations) -> {
            return func.apply(locationCollection.put(locations));
        });
    }

}
