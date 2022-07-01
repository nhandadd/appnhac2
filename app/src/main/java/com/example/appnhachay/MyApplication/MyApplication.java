package com.example.appnhachay.MyApplication;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class MyApplication extends Application {
    NotificationManager notificationManager;
    @Override
    public void onCreate() {
        super.onCreate();

        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("CHANNEL_ID","Notification",NotificationManager.IMPORTANCE_DEFAULT);
            channel.setSound(null,null);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
