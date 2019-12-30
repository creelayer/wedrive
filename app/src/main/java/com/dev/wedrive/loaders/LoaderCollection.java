package com.dev.wedrive.loaders;

import com.dev.wedrive.service.MapService;

import java.util.ArrayDeque;

public class LoaderCollection extends ArrayDeque<LoaderInterface> {

    protected MapService mapService;

    public LoaderCollection(MapService mapService) {
        this.mapService = mapService;
    }

    @Override
    public boolean add(LoaderInterface loader) {
        return super.add(loader);
    }

    @Override
    public void clear() {
        super.clear();
        mapService.clearLocations();
    }

    public void load() {
        run();
    }

    public void run() {
        if (!isEmpty()) {
            getLast().run((locations) -> {
                mapService.updateLocations(locations);
                return null;
            });
        }
    }
}
