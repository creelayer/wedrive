package com.dev.wedrive.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class ApiRoute implements TypeInterface {

    public final static String DEFAULT_NAME = "default";

    public final static String STATUS_CURRENT = "current";
    public final static String STATUS_ACTIVE = "active";


    @Setter
    @Accessors(chain = true)
    @SerializedName("uuid")
    public String uuid;

    @SerializedName("user_id")
    public Integer userId;

    @SerializedName("type")
    @Getter
    public String type;

    @SerializedName("status")
    @Getter
    public String status;

    @SerializedName("name")
    public String name;

    @SerializedName("seats")
    public double seats;

    @SerializedName("locations")
    @Getter
    @Setter
    public List<ApiLocation> locations;

}
