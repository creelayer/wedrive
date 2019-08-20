package com.dev.wedrive.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.Getter;

public class ApiProfile implements Serializable {

    public static final String TYPE_DRIVER = "driver";
    public static final String TYPE_PASSENGER = "passenger";

    @SerializedName("user_id")
    public Integer userId;

    @SerializedName("type")
    @Getter
    public String type;

    @SerializedName("name")
    @Getter
    public String name;

    @SerializedName("car_name")
    @Getter
    public String carName;

    @SerializedName("car_color")
    @Getter
    public String carColor;

    @SerializedName("car_id")
    @Getter
    public String carId;


}
