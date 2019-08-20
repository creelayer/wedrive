package com.dev.wedrive.entity;

import com.google.gson.annotations.SerializedName;

import lombok.Setter;
import lombok.experimental.Accessors;

public class ApiRoute {

    @Setter
    @Accessors(chain = true)
    @SerializedName("uuid")
    public String uuid;

    @SerializedName("user_id")
    public Integer userId;

    @SerializedName("name")
    public String name;

    @SerializedName("seats")
    public double seats;

}
