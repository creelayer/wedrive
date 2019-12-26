package com.dev.wedrive.adapters;

import com.dev.wedrive.entity.ApiLocation;
import com.google.android.gms.maps.model.Marker;

import lombok.Getter;
import lombok.Setter;

public class LocationAdapter {

    @Getter
    private ApiLocation location;

    @Setter
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

    public void remove(){
        if(marker != null){
            marker.remove();
        }
    }

}
