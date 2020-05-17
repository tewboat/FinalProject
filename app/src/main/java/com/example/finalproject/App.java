package com.example.finalproject;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class App extends Application {
    public static final String NOTIFICATION_CHANNEL_ID = "NOTIFICATION_CHANNEL";

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannels();
    }
    private void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(
                    NOTIFICATION_CHANNEL_ID,
                    "ReminderChannel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            channel1.setDescription("This is Reminder Channel");

            NotificationManager manager = getSystemService(NotificationManager.class);
            assert manager != null;
            manager.createNotificationChannel(channel1);
        }
    }

}

