package com.beveragebooker.customer_app.notifications;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.beveragebooker.customer_app.R;
import com.beveragebooker.customer_app.activities.AccountActivity;
import com.beveragebooker.customer_app.activities.MainActivity;
import com.beveragebooker.customer_app.activities.OrderConfirmationActivity;
import com.beveragebooker.customer_app.api.RetrofitClient;
import com.beveragebooker.customer_app.models.Order;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import es.dmoral.toasty.Toasty;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationOutput {

    private static Timer myTimer;

    public static void displayNotification(OrderConfirmationActivity context, String title, String body, int userID, int cartID) {

        Log.d("WHAT HAS HAPPENED", "this has been called");
        //int orderStatus = completeOrder(orderID);
        myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {

                Call<Order> call = RetrofitClient
                        .getInstance()
                        .getApi()
                        .getOrderStatus(userID, cartID);

                call.enqueue(new Callback<Order>() {
                    @Override
                    public void onResponse(Call<Order> call, Response<Order> response) {
                        if (response.body() != null) {

                            Order thisOrder = response.body();
                            int status = thisOrder.getOrderStatus();
                            int orderID = thisOrder.getOrderID();
                            System.out.println("Status: " + status);
                            System.out.println("OrderID: " + orderID);

                            if (status == 0) {
                                completeOrder(context, title, body, orderID);
                                myTimer.cancel();
                                myTimer.purge();
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<Order> call, Throwable t) {

                    }
                });
            }
        }, 0, 10000);
    }


    private static void completeOrder(OrderConfirmationActivity context, String title, String body, int orderID) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder((Context) context, MainActivity.CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_notifications)

                        .setContentTitle(title)
                        .setAutoCancel(true)
                        .setBadgeIconType(NotificationCompat.BADGE_ICON_LARGE)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(body))
                        .setContentText(body)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        System.out.println("OrderID 2: " + orderID);

        NotificationManagerCompat mNotificationMgr = NotificationManagerCompat.from((Context) context);
        mNotificationMgr.notify(0, mBuilder.build());

        context.updateOrder();

    }
}

