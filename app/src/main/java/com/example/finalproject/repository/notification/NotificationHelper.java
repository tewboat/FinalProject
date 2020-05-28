package com.example.finalproject.repository.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.core.app.NotificationCompat;

import com.example.finalproject.R;
import com.example.finalproject.view.MainActivity;

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
        Bitmap icon = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.mipmap.app_icon_round);
        Intent activityIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this,
                0, activityIntent, 0);
        return new NotificationCompat.Builder(getApplicationContext(), NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_action_name)
                .setLargeIcon(icon)
                .setContentTitle(title)
                .setContentText("Have you done it?")
                .setContentIntent(contentIntent)
                .setAutoCancel(true);
    }


}
