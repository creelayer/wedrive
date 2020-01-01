package com.dev.wedrive.adapters;

import com.dev.wedrive.entity.ApiLocation;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import lombok.Getter;
import lombok.Setter;

public class LocationAdapter {

    @Setter
    @Getter
    private ApiLocation location;

    @Getter
    private Marker marker;

    @Getter
    @Setter
    private long updated;

    public LocationAdapter(ApiLocation location) {
        this.location = location;
    }

    public String getUuid() {
        return location.uuid;
    }

    public void setLocation(ApiLocation location) {

        if (marker != null)
            marker.setPosition(location.getLatLng());

        this.location = location;
    }

    public void setMarker(Marker marker) {
        marker.setTag(location.getUuid());
        this.marker = marker;
    }

    public void remove() {
        if (marker != null) {
            marker.remove();
        }
    }

}
