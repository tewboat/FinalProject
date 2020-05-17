package com.example.finalproject.repository.notification;

import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.core.app.NotificationCompat;

import com.example.finalproject.R;

import static com.example.finalproject.App.NOTIFICATION_CHANNEL_ID;

public class NotificationHelper extends ContextWrapper {

    private NotificationManager mManager;

    public NotificationHelper(Context base) {
        super(base);
    }

    public NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }
    public NotificationCompat.Builder getChannelNotification(String title) {
        Bitmap icon = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.ic_action_name);
        return new NotificationCompat.Builder(getApplicationContext(), NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.mipmap.app_icon_round)
                .setLargeIcon(icon)
                .setContentTitle(title)
                .setContentText("Have you done it?");
    }


}
