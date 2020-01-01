package com.dev.wedrive.entity;

import com.dev.wedrive.CreateNewRouteActivity;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class ApiRoute implements TypeInterface {

//    public final static String DEFAULT_NAME = "default";

    public final static String STATUS_CURRENT = "current";
    public final static String STATUS_ACTIVE = "active";


    @Setter
    @Accessors(chain = true)
    @SerializedName("uuid")
    public String uuid;

    @SerializedName("user_id")
    public Integer userId;

    @SerializedName("user")
    public ApiUser user;

    @SerializedName("type")
    @Getter
    public String type;

    @SerializedName("status")
    @Getter
    public String status;

    @SerializedName("name")
    public String name;

    @SerializedName("car")
    public ApiCar car;

    @SerializedName("request")
    public ApiRequest request;

//    @SerializedName("locations")
//    @Getter
//    @Setter
//    public List<ApiLocation> locations;


    public ApiRoute() {
    }

    public ApiRoute(CreateNewRouteActivity activity) {
        load(activity);
    }

    public ApiRoute load(CreateNewRouteActivity activity) {
        name = activity.getName().getText().toString();
        type = ApiRoute.TYPE_DRIVER;
        return this;
    }

}
