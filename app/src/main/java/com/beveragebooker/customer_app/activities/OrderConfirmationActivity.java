package com.beveragebooker.customer_app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.beveragebooker.customer_app.R;
import com.beveragebooker.customer_app.api.RetrofitClient;
import com.beveragebooker.customer_app.models.Order;
import com.beveragebooker.customer_app.models.User;
import com.beveragebooker.customer_app.notifications.NotificationOutput;
import com.beveragebooker.customer_app.storage.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderConfirmationActivity extends AppCompatActivity {

    public static final String SHARED_PREFS = "sharedprefs";
    public static final String CART_ID = "cartID";
    public static final String TIMER_STATUS = "timerStatus";

    private TextView mOrderConfirmTextView;

    int cartID;
    int userID;
    int orderTime;
    int timerStatus;

    User user = SharedPrefManager.getInstance(this).getUser();


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);

        mOrderConfirmTextView = findViewById(R.id.textViewOrderConfirm);

        String title = "Order Ready";
        String body = user.getFirstName() + ", your order is ready to enjoy. " +
                "If you selected delivery it will be with you in approximately 10 minutes.";
        userID = user.getId();
        System.out.println("UserID: " + userID);

        System.out.println("TimerStatus Start: " + timerStatus);

        Intent intent = getIntent();
        cartID = intent.getIntExtra(PlaceOrderActivity.CART_ID, 0);

        //Timer has just started for notification and cartID has been passed
        if (timerStatus == 0 && cartID > 0) {

            timerStatus = 1;
            saveData(cartID, timerStatus);
            NotificationOutput.displayNotification(this, title, body, userID, cartID);
        }

        //loads the cartID if user has left the activity and then comes back to check the activity
        if (cartID == 0) {
            loadData();
        }

        //Displays the order ready message if the push notification has been sent and it has set the cartID to -1
        if (cartID == -1) {
            mOrderConfirmTextView.setText("Your order is ready for pick up, " + user.getFirstName() + ".");
        }

        //if the timer is running and the cartID is valid it gets the estimated order time so it can be displayed for the user
        else if (timerStatus == 1 && cartID > 0) {
            getOrderTime();
        }

        System.out.println("CartID Start: " + cartID);

        User user = SharedPrefManager.getInstance(this).getUser();

        System.out.println("CartID Final: " + cartID);
    }


    //Loads cartID and the timer status when a user returns to the activity
    private void loadData() {
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

        cartID = prefs.getInt(CART_ID, 0);
        timerStatus = prefs.getInt(TIMER_STATUS, 0);

        System.out.println("load cartID: " + cartID);
        System.out.println("load timerStatus: " + timerStatus);

    }


    //Saves cartID and timer status for the user
    private void saveData(int cartID, int timerStatus) {
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putInt(CART_ID, cartID);
        editor.putInt(TIMER_STATUS, timerStatus);

        editor.apply();
    }


    //Gets the estimated order time from the server and uses it to set the timer
    private void getOrderTime() {

        Call<Order> call = RetrofitClient
                .getInstance()
                .getApi()
                .getOrderStatus(userID, cartID);

        call.enqueue(new Callback<Order>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                if (response.code() == 200) {
                    Order thisOrder = response.body();
                    orderTime = thisOrder.getOrderTime();
                    int orderStatus = thisOrder.getOrderStatus();
                    System.out.println("OrderTime: " + orderTime);
                    System.out.println("OrderStatus: " + orderStatus);

                    if (orderStatus == 1) {
                        mOrderConfirmTextView.setText("Thank you for your order, " + user.getFirstName() + "."
                                + "\nYour order will be ready in approximately " + orderTime + " minutes.");
                    } else if (orderStatus == 0) {
                        mOrderConfirmTextView.setText("Your order is ready for pick up, " + user.getFirstName() + ".");
                    }
                }
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {

            }
        });
    }

    public void updateOrder() {

        int finishCartID = -1;
        int finishOrderStatus = 0;

        System.out.println("Finish cartID: " + finishCartID);
        System.out.println("Finish orderStatus: " + finishOrderStatus);

        saveData(finishCartID, finishOrderStatus);


    }
}
