package com.dev.wedrive.entity;

import com.google.gson.annotations.SerializedName;

public class ApiToken {


    @SerializedName("user_id")
    public int userId;

    @SerializedName(value = "access-token", alternate = "code")
    public String code;

    @SerializedName("refresh")
    public String refresh;

    public ApiToken() {
    }

    public ApiToken(String token) {
        this.code = token;
    }
}
