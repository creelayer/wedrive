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
        loader.setMapService(mapService);
        return super.add(loader);
    }

    public void load() {
        run();
    }

    public void run() {
        getLast().run();
    }
}
