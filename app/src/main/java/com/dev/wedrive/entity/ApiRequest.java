package com.dev.wedrive.entity;

import com.dev.wedrive.sheet.RoutePassengerSheet;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class ApiRequest {

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


    public ApiRequest() {
    }

    public ApiRequest(RoutePassengerSheet routeSheet) {
        message = routeSheet.getRequestMessageInp().getText().toString();
    }
}
