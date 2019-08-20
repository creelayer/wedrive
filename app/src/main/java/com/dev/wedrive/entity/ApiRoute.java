package com.dev.wedrive.entity;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class ApiRoute implements TypeInterface {

    public final static String DEFAULT_NAME = "default";

    @Setter
    @Accessors(chain = true)
    @SerializedName("uuid")
    public String uuid;

    @SerializedName("user_id")
    public Integer userId;

    @SerializedName("type")
    @Getter
    public String type;

    @SerializedName("name")
    public String name;

    @SerializedName("seats")
    public double seats;

}
