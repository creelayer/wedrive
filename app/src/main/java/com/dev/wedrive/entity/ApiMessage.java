package com.dev.wedrive.entity;

import com.dev.wedrive.MessengerActivity;
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

    @SerializedName("user_id")
    @Setter
    @Getter
    public int userId = 0;

    @SerializedName("user")
    @Setter
    @Getter
    public ApiUser user;

    @SerializedName("recipient_id")
    public int recipientId = 0;

    @SerializedName("recipient")
    @Setter
    @Getter
    public ApiUser recipient;

    @SerializedName("message")
    @Setter
    @Getter
    public String message = "";

    @SerializedName("status")
    @Setter
    @Getter
    public String status;

    @SerializedName("request_uuid")
    @Setter
    @Getter
    public String requestUuid;

    @SerializedName("created_at")
    @Setter
    @Getter
    public Timestamp createdAt;

    public ApiMessage() {
    }

    public ApiMessage(MessengerActivity activity) {
        message = activity.getMessageInp().getText().toString().trim();
        recipientId = activity.getRecipient().getId();
        if (activity.getRequest() != null)
            requestUuid = activity.getRequest().uuid;
    }

}
