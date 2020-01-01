package com.dev.wedrive.loaders;

import androidx.arch.core.util.Function;

import com.dev.wedrive.collection.LocationCollection;
import com.dev.wedrive.service.LocationService;

import lombok.Getter;

public class NearestLoader implements LoaderInterface {

    @Getter
    protected LocationCollection locationCollection;
    protected LocationService locationService;

    private String type;

    public NearestLoader(String type) {
        this.type = type;
        this.locationCollection = new LocationCollection();
        this.locationService = new LocationService();
    }

    public void load(final Function<LocationCollection, Void> func) {
        run(func);
    }

    public void run(final Function<LocationCollection, Void> func) {

        locationService.getNearestLocations(type, (locations) -> func.apply(locationCollection.put(locations)));

    }

}
