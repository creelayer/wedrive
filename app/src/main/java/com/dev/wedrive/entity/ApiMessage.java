package com.dev.wedrive.entity;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

public class ApiMessage {

    public static final String STATUS_NEW = "new";
    public static final String STATUS_READ = "read";

    @SerializedName("uuid")
    @Getter
    public String uuid;

    @SerializedName("user")
    @Setter
    @Getter
    public ApiUser user;

    @SerializedName("recipient_id")
    public int recipientId;

    @SerializedName("recipient")
    @Setter
    @Getter
    public ApiUser recipient;

    @SerializedName("message")
    @Setter
    @Getter
    public String message;

    @SerializedName("status")
    @Setter
    @Getter
    public String status;

    @SerializedName("created_at")
    @Setter
    @Getter
    public Timestamp createdAt;

}
