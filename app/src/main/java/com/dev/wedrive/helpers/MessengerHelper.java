package com.dev.wedrive.helpers;

import com.dev.wedrive.entity.ApiMessage;
import com.dev.wedrive.entity.ApiUser;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class MessengerHelper {

    public static String formatShortTime(Timestamp timestamp) {
        return new SimpleDateFormat("HH:mm").format(timestamp);
    }

    public static String formatShortDate(Timestamp timestamp) {
        return new SimpleDateFormat("yyyy.MM.dd").format(timestamp);
    }

    public static String formatChatMessage(ApiMessage message, ApiUser user) {
        return user.id == message.userId ? "Ви: " + message.message : message.message;
    }

}
