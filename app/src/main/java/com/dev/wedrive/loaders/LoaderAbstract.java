package com.dev.wedrive.loaders;

import com.dev.wedrive.service.MapService;

public abstract class LoaderAbstract implements LoaderInterface {

    protected MapService mapService;

    public LoaderAbstract(MapService mapService){
        this.mapService = mapService;
    }

}
