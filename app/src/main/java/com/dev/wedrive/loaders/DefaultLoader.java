package com.dev.wedrive.loaders;

import com.dev.wedrive.entity.ApiProfile;
import com.dev.wedrive.service.MapService;

public class DefaultLoader extends LoaderAbstract {

    private ApiProfile profile;

    public DefaultLoader(MapService mapService) {
        super(mapService);
    }

    @Override
    public void load() {
        run();
    }

    @Override
    public void run() {
        mapService.loadNearestLocations();
    }
}
