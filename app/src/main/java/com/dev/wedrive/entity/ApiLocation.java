package com.dev.wedrive.entity;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;

public class ApiLocation<T> implements ApiLocationInterface, TypeInterface {

    @SerializedName("uuid")
    @Getter
    public String uuid;

    @SerializedName("route_uuid")
    public String routeUuid;

    @SerializedName("type")
    @Getter
    public String type;

    @SerializedName("latitude")
    @Getter
    public double latitude;

    @SerializedName("longitude")
    @Getter
    public double longitude;

    @SerializedName("data")
    @Getter
    public T data;

    /**
     *
     */
    public ApiLocation() {

    }

    /**
     * @param latLng
     */
    public ApiLocation(LatLng latLng) {
        this.latitude = latLng.latitude;
        this.longitude = latLng.longitude;
    }

    /**
     * @param latLng
     * @param route
     */
    public ApiLocation(LatLng latLng, ApiRoute route) {
        this.routeUuid = route.uuid;
        this.type = route.type + "_location";
        this.latitude = latLng.latitude;
        this.longitude = latLng.longitude;
    }
    
    public LatLng getLatLng() {
        return new LatLng(latitude, longitude);
    }

}
