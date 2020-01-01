package com.dev.wedrive.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public class ApiUser implements Serializable {

    @SerializedName("id")
    @Getter
    public Integer id;

    @SerializedName("email")
    @Getter
    public String email;

    @SerializedName("password")
    @Getter
    public String password;

    @SerializedName("auth_key")
    @Getter
    public String authKey;

    @SerializedName("profile")
    @Setter
    @Getter
    public ApiProfile profile;

    public ApiUser() {
    }

    public ApiUser(String email, String password) {
        this.email = email;
        this.password = password;
    }

}
