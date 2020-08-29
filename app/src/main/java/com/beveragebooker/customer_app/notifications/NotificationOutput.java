package com.beveragebooker.customer_app.notifications;

import android.content.Context;

import com.beveragebooker.customer_app.R;
import com.beveragebooker.customer_app.activities.MainActivity;
import com.beveragebooker.customer_app.api.RetrofitClient;

import java.util.Timer;
import java.util.TimerTask;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationOutput {
    private static Timer myTimer;

    public static void displayNotification(Context context, String title, String body, int userID) {

        //int orderStatus = completeOrder(orderID);
        int orderOpen = 1;
        while (orderOpen == 1) {

                myTimer = new Timer();
                myTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {

                        Call<ResponseBody> call = RetrofitClient
                                .getInstance()
                                .getApi()
                                .getStatus(userID);

                        call.enqueue(new Callback<ResponseBody>() {

                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                                if(response.code() == 201) {

                                    completeOrder(context, title, body);

                                }

                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {

                            }
                        });

                    }
                }, 0, 10000);

                orderOpen = 0;

            }
        }


    private static void completeOrder(Context context, String title, String body) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context, MainActivity.CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_notifications)

                        .setContentTitle(title)
                        .setContentText(body)
                        .setAutoCancel(true)
                        .setBadgeIconType(NotificationCompat.BADGE_ICON_LARGE)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat mNotificationMgr = NotificationManagerCompat.from(context);
        mNotificationMgr.notify(0, mBuilder.build());


    }
}
