package com.dev.wedrive.entity;

import com.dev.wedrive.ProfileEditActivity;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.Getter;

public class ApiProfile implements Serializable {

    public static final String TYPE_DRIVER = "driver";
    public static final String TYPE_PASSENGER = "passenger";

    @SerializedName("user_id")
    public Integer userId;

    @Getter
    public ApiUser user;

    @SerializedName("type")
    @Getter
    public String type;

    @SerializedName("name")
    @Getter
    public String name;

    @SerializedName("last_name")
    @Getter
    public String lastName;

    @SerializedName("phone")
    @Getter
    public String phone;

    @SerializedName("email")
    @Getter
    public String email;

    @SerializedName("image")
    @Getter
    public String image;

    public ApiProfile load(ProfileEditActivity activity) {
        name = activity.getName().getText().toString();
        lastName = activity.getLastName().getText().toString();
        return this;
    }

}
