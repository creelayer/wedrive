package com.dev.wedrive.entity;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

public class ApiMessageChat {

    @SerializedName("uuid")
    @Getter
    public String uuid;

    @SerializedName("user_id")
    @Setter
    @Getter
    public int userId = 0;

    @SerializedName("message_uuid")
    @Setter
    @Getter
    public String message_uuid;

    @SerializedName("message")
    @Setter
    @Getter
    public ApiMessage message;

    @SerializedName("created_at")
    @Setter
    @Getter
    public Timestamp createdAt;

    @SerializedName("updated_at")
    @Setter
    @Getter
    public Timestamp updatedAt;

}
