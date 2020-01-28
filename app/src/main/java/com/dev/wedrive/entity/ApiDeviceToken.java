package com.dev.wedrive.entity;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class ApiDeviceToken {

    @SerializedName("uuid")
    @Getter
    public String uuid;

    @SerializedName("user_id")
    @Setter
    @Getter
    public int userId;

//    @SerializedName("user")
//    @Setter
//    @Getter
//    public ApiUser user;

    @SerializedName("token")
    public String token;


    public ApiDeviceToken() {
    }

}
