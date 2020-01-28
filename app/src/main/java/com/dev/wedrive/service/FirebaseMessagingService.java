package com.dev.wedrive.service;


import android.util.Log;

import com.dev.wedrive.entity.ApiDeviceToken;
import com.google.firebase.messaging.RemoteMessage;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    public static final String TAG = "FCMee";

    public DeviceTokenService deviceTokenService;

    public FirebaseMessagingService() {
        super();
        deviceTokenService = new DeviceTokenService();
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());


        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

//            if (/* Check if data needs to be processed by long running job */ true) {
//                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
//                scheduleJob();
//            } else {
//                // Handle message within 10 seconds
//                handleNow();
//            }

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());

//            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NotificationManager.CHANNEL_ID)
//                    .setSmallIcon(R.mipmap.ic_launcher)
//                    .setContentTitle("My notification")
//                    .setContentText("Much longer text that cannot fit one line...")
//                    .setStyle(new NotificationCompat.BigTextStyle()
//                            .bigText("Much longer text that cannot fit one line2..."))
//                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
////
//            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
////
////// notificationId is a unique int for each notification that you must define
//            notificationManager.notify(1, builder.build());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);

        ApiDeviceToken apiDeviceToken = new ApiDeviceToken();
        apiDeviceToken.token = token;
        deviceTokenService.add(apiDeviceToken, (deviceToken) -> {

        });
    }

}
