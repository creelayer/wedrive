package com.dev.wedrive.entity;

import com.dev.wedrive.dialog.CreateDriverLocationDialog;
import com.dev.wedrive.dialog.CreatePassengerLocationDialog;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.LinkedTreeMap;

import lombok.Getter;
import lombok.Setter;

public class ApiLocation implements ApiLocationInterface, TypeInterface {

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
    @Setter
    @Getter
    public Object data;

    @SerializedName("user_id")
    @Setter
    @Getter
    public int userId;

    @SerializedName("user")
    @Setter
    @Getter
    public ApiUser user;

    @SerializedName("route")
    @Setter
    @Getter
    public ApiRoute route;

    @SerializedName("hour")
    @Setter
    @Getter
    public int hour;

    @SerializedName("min")
    @Setter
    @Getter
    public int min;

    @SerializedName("interval")
    @Setter
    @Getter
    public int interval;

    @SerializedName("message")
    @Setter
    @Getter
    public String message;

    /**
     *
     */
    public ApiLocation() {

    }


    /**
     * @param latLng
     */
    public ApiLocation(LatLng latLng) {
        latitude = latLng.latitude;
        longitude = latLng.longitude;
    }

    /**
     * @param latLng
     */
    public ApiLocation(LatLng latLng, String type) {
        latitude = latLng.latitude;
        longitude = latLng.longitude;
        this.type = type;
    }

    /**
     * @param latLng
     * @param route
     */
    public ApiLocation(LatLng latLng, ApiRoute route) {
        routeUuid = route.uuid;
        type = TypeInterface.TYPE_DRIVER_LOCATION;
        latitude = latLng.latitude;
        longitude = latLng.longitude;
    }

    public ApiLocation(CreateDriverLocationDialog dialog) {
        hour = Integer.parseInt(dialog.getHour().getText().toString());
        min = Integer.parseInt(dialog.getMinute().getText().toString());
        interval = Integer.parseInt(dialog.getInterval().getText().toString());
    }

    public ApiLocation(CreatePassengerLocationDialog dialog) {
        hour = Integer.parseInt(dialog.getHour().getText().toString());
        min = Integer.parseInt(dialog.getMinute().getText().toString());
        interval = Integer.parseInt(dialog.getInterval().getText().toString());
        message = dialog.getMessage().getText().toString();
    }

    public LatLng getLatLng() {
        return new LatLng(latitude, longitude);
    }
}
