package com.dev.wedrive.service;


import android.util.Log;

import com.google.firebase.messaging.RemoteMessage;

public class NotificationMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    public static final String TAG = "FCMee";

    public static final String  CHANNEL_ID = "notification_chanel";

    public DeviceTokenService deviceTokenService;

    public NotificationMessagingService() {
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

//    /**
//     * Called if InstanceID token is updated. This may occur if the security of
//     * the previous token had been compromised. Note that this is called when the InstanceID token
//     * is initially generated so this is where you would retrieve the token.
//     */
//    @Override
//    public void onNewToken(String token) {
//        deviceTokenService.add(new ApiDeviceToken(token), (deviceToken) -> {});
//    }


//    public void createNotificationChanel() {
//
//        NotificationManager notificationManager =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            NotificationChannel channel = new NotificationChannel(NotificationMessagingService.CHANNEL_ID, "App notification",
//                    NotificationManager.IMPORTANCE_HIGH);
//            channel.setDescription("Add app notification permission");
//            channel.enableLights(true);
//            channel.setLightColor(Color.RED);
//            channel.enableVibration(false);
//            notificationManager.createNotificationChannel(channel);
//        }
//    }


}
