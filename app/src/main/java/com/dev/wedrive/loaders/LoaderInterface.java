package com.dev.wedrive.loaders;

import com.dev.wedrive.collection.MarkerCollection;
import com.google.android.gms.maps.GoogleMap;

public interface LoaderInterface {

    public void load();

    public void run();

    public MarkerCollection getMarkerCollection();

    public LoaderInterface setMap(GoogleMap map);

}
