package com.dev.wedrive.loaders;

import com.dev.wedrive.entity.ApiLocation;
import com.dev.wedrive.entity.ApiProfile;
import com.dev.wedrive.service.MapService;
import com.google.android.gms.maps.model.LatLng;

public class DefaultLoader extends LoaderAbstract {


    private LatLng latLng;

    private ApiProfile profile;

    public DefaultLoader(MapService mapService, ApiProfile profile, LatLng latLng) {
        super(mapService);
        this.latLng = latLng;
        this.profile = profile;
    }

    @Override
    public void load() {
        run();
    }

    @Override
    public void run() {
        mapService.updateMyLocation(latLng);
        mapService.loadNearestLocations(profile.getType().equals(ApiLocation.TYPE_DRIVER) ? ApiLocation.TYPE_PASSENGER : ApiLocation.TYPE_DRIVER);
    }
}
