package com.beveragebooker.customer_app.notifications;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.beveragebooker.customer_app.R;
import com.beveragebooker.customer_app.activities.MainActivity;
import com.beveragebooker.customer_app.activities.OrderConfirmationActivity;
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

    public static void displayNotification(OrderConfirmationActivity context, String title, String body, int userID) {
        Log.d("WHAT HAS HAPPENED", "this has been called");
        //int orderStatus = completeOrder(orderID);
        final int[] orderOpen = {1};
        while (true) {

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

                                    String orderID = String.valueOf(response.body());
                                    completeOrder(context, title, body, orderID);

                                }
                                Log.d("WHAT HAS HAPPENED", String.valueOf(+ response.code()));
                            }
                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                        }
                });
            }
                }, 0, 10000);
            return;
      }
    }


    private static void completeOrder(OrderConfirmationActivity context, String title, String body, String orderID) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder((Context) context, MainActivity.CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_notifications)

                        .setContentTitle(title)
                        .setContentText(body)
                        .setAutoCancel(true)
                        .setBadgeIconType(NotificationCompat.BADGE_ICON_LARGE)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat mNotificationMgr = NotificationManagerCompat.from((Context) context);
        mNotificationMgr.notify(0, mBuilder.build());

        changeStatus(orderID);
}


    private static void changeStatus(String orderID){

        Call<ResponseBody> call = RetrofitClient
                .getInstance().getApi()
                .setStatus(orderID);

        call.enqueue(new Callback<ResponseBody>() {


            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                Log.d("Status", "HAS BEEN UPDATED");


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }


}

