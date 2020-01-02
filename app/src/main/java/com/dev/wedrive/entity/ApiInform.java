package com.dev.wedrive.entity;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

public class ApiInform {

    public static final String TYPE_REQUEST = "request";

    @SerializedName("uuid")
    @Getter
    public String uuid;

    @SerializedName("user")
    @Setter
    @Getter
    public ApiUser user;

    @SerializedName("message")
    public String message;

    @SerializedName("status")
    public String status;

    @SerializedName("type")
    public String  type;

    @SerializedName("created_at")
    @Setter
    @Getter
    public Timestamp createdAt;

    public ApiInform() {
    }

}
