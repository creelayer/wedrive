package com.dev.wedrive.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public class ApiUser implements Serializable {


    public static String STATUS_CREATED = "created";

    @SerializedName("id")
    @Getter
    public Integer id;

    @SerializedName("phone")
    @Getter
    public String phone;

    @SerializedName("password")
    @Getter
    public String password;

    @SerializedName("auth_key")
    @Getter
    public String authKey;

    @SerializedName("status")
    @Getter
    public String status;

    @SerializedName("profile")
    @Setter
    @Getter
    public ApiProfile profile;

    public ApiUser() {
    }

    public ApiUser(String phone, String password) {
        this.phone = phone;
        this.password = password;
    }

}
