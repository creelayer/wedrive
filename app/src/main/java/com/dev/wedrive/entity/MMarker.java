package com.dev.wedrive.entity;

import com.google.android.gms.maps.model.Marker;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class MMarker implements TypeInterface {

    public final static String GROUP_ROUTE = "route";


    @Setter
    @Getter
    private Integer userId;

    @Setter
    @Getter
    @Accessors(chain = true)
    private String group;

    @Getter
    private Marker marker;

    @Getter
    private ApiLocationInterface location;

    public MMarker(ApiLocationInterface location, Marker marker) {
        this.location = location;
        this.marker = marker;
    }

    public String getUuid() {
        return location.getUuid();
    }

    public String getType() {
        return location.getType();
    }

    public void setLocation(ApiLocationInterface location){
        this.location = location;
        marker.setPosition(location.getLatLng());
    }


}
