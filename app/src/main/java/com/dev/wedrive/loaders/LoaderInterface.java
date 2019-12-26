package com.dev.wedrive.loaders;

import com.dev.wedrive.service.MapService;

public interface LoaderInterface {

    public void setMapService(MapService mapService);

    public void load();

    public void run();

}
