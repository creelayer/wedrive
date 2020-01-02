package com.dev.wedrive.entity;

import com.dev.wedrive.sheet.RoutePassengerSheet;
import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

public class ApiRequest {

    public static final String STATUS_NEW = "new";
    public static final String STATUS_ACCEPTED = "accepted";
    public static final String STATUS_DENIED = "denied";

    @SerializedName("uuid")
    @Getter
    public String uuid;

    @SerializedName("user")
    @Setter
    @Getter
    public ApiUser user;

    @SerializedName("location")
    @Setter
    @Getter
    public ApiLocation location;

    @SerializedName("message")
    @Setter
    @Getter
    public String message;

    @SerializedName("status")
    @Setter
    @Getter
    public String status;

    @SerializedName("created_at")
    @Setter
    @Getter
    public Timestamp createdAt;

    public ApiRequest() {
    }

    public ApiRequest(RoutePassengerSheet routeSheet) {
        message = routeSheet.getRequestMessageInp().getText().toString();
    }
}
