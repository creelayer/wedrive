package com.dev.wedrive.entity;

import com.google.gson.annotations.SerializedName;

public class ApiToken {


    @SerializedName("access-token")
    public String accessToken;

    public ApiToken(){

    }

    public ApiToken(String token){
        this.accessToken = token;
    }
}
