package com.dev.wedrive.collection;

import com.dev.wedrive.adapters.LocationAdapter;
import com.dev.wedrive.entity.ApiLocation;
import com.google.android.gms.maps.model.Marker;

import java.util.HashMap;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class LocationCollection extends HashMap<String, LocationAdapter> {

    @Getter
    @Setter
    private long updated = System.currentTimeMillis();

    public LocationCollection put(LocationAdapter locationAdapter) {
        locationAdapter.setUpdated(updated);
        super.put(locationAdapter.getUuid(), locationAdapter);
        return this;
    }

    public LocationCollection put(List<ApiLocation> locations) {
        for (ApiLocation location : locations) {

            LocationAdapter locationAdapter = get(location.uuid);

            if (locationAdapter == null)
                locationAdapter = new LocationAdapter(location);
            else
                locationAdapter.setLocation(location);

            put(locationAdapter);
        }
        return this;
    }

    public void remove(LocationAdapter locationAdapter) {
        locationAdapter.remove();
        remove(locationAdapter.getUuid());
    }

    public void touch() {
        updated = System.currentTimeMillis();
    }

}
