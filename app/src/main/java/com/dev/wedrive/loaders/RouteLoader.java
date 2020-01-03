package com.dev.wedrive.loaders;

import androidx.core.util.Consumer;

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

    public void load(final Consumer<LocationCollection> func) {
        run(func);
    }

    public void run(final Consumer<LocationCollection> func) {
        locationService.getLocationsByRoute(route, (locations) -> func.accept(locationCollection.put(locations)));
    }

}
