package com.dev.wedrive.entity;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;

public class ApiCurrentLocation implements ApiLocationInterface {

    @SerializedName("uuid")
    @Getter
    public String uuid;

    @SerializedName("user_id")
    public Integer userId;

    @SerializedName("type")
    @Getter
    public String type;

    @SerializedName("latitude")
    @Getter
    public double latitude;

    @SerializedName("longitude")
    @Getter
    public double longitude;

    public ApiCurrentLocation() {
    }

    public ApiCurrentLocation(LatLng latLng) {
        latitude = latLng.latitude;
        longitude = latLng.longitude;
    }

    public ApiCurrentLocation(MMarker marker) {
        uuid = marker.getUuid();
        userId = marker.getUserId();
        type = marker.getType();
        latitude = marker.getLocation().getLatitude();
        longitude = marker.getLocation().getLongitude();
    }

    public LatLng getLatLng() {
        return new LatLng(latitude, longitude);
    }

}
