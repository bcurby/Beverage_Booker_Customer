package com.beveragebooker.customer_app.notifications;

import android.content.Context;

import com.beveragebooker.customer_app.R;
import com.beveragebooker.customer_app.activities.MainActivity;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationOutput {

    public static void displayNotification(Context context, String title, String body){


        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context, MainActivity.CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_notifications)

                        .setContentTitle(title)
                        .setContentText(body)
                        .setAutoCancel(true)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);


        NotificationManagerCompat mNotificationMgr = NotificationManagerCompat.from(context);
        mNotificationMgr.notify(0, mBuilder.build());

    }




}
