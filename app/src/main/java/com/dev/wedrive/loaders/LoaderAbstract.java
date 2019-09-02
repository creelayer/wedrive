package com.dev.wedrive.loaders;

import com.dev.wedrive.collection.MarkerCollection;
import com.dev.wedrive.service.LocationService;
import com.google.android.gms.maps.GoogleMap;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public abstract class LoaderAbstract implements LoaderInterface {

    @Setter
    @Accessors(chain = true)
    protected GoogleMap map;

    protected LocationService locationService;

    @Getter
    protected MarkerCollection markerCollection;

    public LoaderAbstract(){
        this.markerCollection = new MarkerCollection();
        this.locationService = new LocationService();
    }

}
