package com.dev.wedrive.loaders;

import com.google.android.gms.maps.GoogleMap;

import java.util.ArrayDeque;

public class LoaderCollection extends ArrayDeque<LoaderInterface> {

    protected GoogleMap map;

    public LoaderCollection(GoogleMap map) {
        super();
        this.map = map;
    }

    @Override
    public LoaderInterface removeLast() {
        LoaderInterface loader = super.removeLast();
        loader.getMarkerCollection().clear();
        super.getLast().load();
        return loader;
    }

    @Override
    public boolean add(LoaderInterface loaderInterface) {

        loaderInterface.setMap(map);

        if (!isEmpty()) {
            getLast().getMarkerCollection().clear();
        }

        loaderInterface.load();
        return super.add(loaderInterface);
    }
}
