package com.beveragebooker.customer_app.notifications;

import android.content.Context;
import android.widget.Toast;

import com.beveragebooker.customer_app.R;
import com.beveragebooker.customer_app.activities.MainActivity;
import com.beveragebooker.customer_app.api.RetrofitClient;

import java.util.List;
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

    public static void displayNotification(Context context, String title, String body, int orderID) {

        //int orderStatus = completeOrder(orderID);
        final int[] orderOpen = {1};
        while (orderOpen[0] == 1) {

                myTimer = new Timer();
                myTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {

                        Call<ResponseBody> call = RetrofitClient
                                .getInstance()
                                .getApi()
                                .getStatus(orderID);

                        call.enqueue(new Callback<ResponseBody>() {

                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                                if(response.body().equals(0) ) {

                                    completeOrder(context, title, body);
                                    orderOpen[0] = 0;
                                }

                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {

                            }
                        });

                    }
                }, 0, 10000);


            }
        }


    private static int completeOrder(Context context, String title, String body) {
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

        int orderStatus = 0;
        return orderStatus;
    }
}
