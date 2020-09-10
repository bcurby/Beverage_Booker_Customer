package com.beveragebooker.customer_app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.beveragebooker.customer_app.R;
import com.beveragebooker.customer_app.api.RetrofitClient;
import com.beveragebooker.customer_app.models.Cart;
import com.beveragebooker.customer_app.models.Order;
import com.beveragebooker.customer_app.models.User;
import com.beveragebooker.customer_app.notifications.NotificationOutput;
import com.beveragebooker.customer_app.storage.SharedPrefManager;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderConfirmationActivity extends AppCompatActivity {
    //private static long START_TIME_IN_MILLIS = 20000;

    public static final String SHARED_PREFS = "sharedprefs";
    public static final String CART_ID = "cartID";
    public static final String TIMER_STATUS = "timerStatus";





    private long startTimeInMillis;

    private TextView mOrderConfirmTextView;
    private TextView mOrderCompleted;
    private TextView mCountDownTextView;
    private Button mReturnToMainMenuButton;
    private Button mStartPauseButton;
    private Button mResetButton;

    int cartID;
    int userID;
    int orderTime;
    int timerStatus;
    //public static int orderStatus;
    ProgressBar orderProgressBar;

    //private static Timer myTimer;

    int progressPercentage;
    int numberOfSeconds;
    int factor;


    private CountDownTimer mCountDownTimer;

    private boolean mTimerRunning;

    private long mTimeLeftInMillis;
    private long mEndTime;
    User user = SharedPrefManager.getInstance(this).getUser();
    Cart thisCart;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);

        mOrderConfirmTextView = findViewById(R.id.textViewOrderConfirm);
        mCountDownTextView = findViewById(R.id.textViewCountDown);
        mStartPauseButton = findViewById(R.id.button_start_pause);
        mResetButton = findViewById(R.id.button_reset);

        /*
        //PROGRESS BAR
        orderProgressBar = findViewById(R.id.my_progress_bar);
        orderProgressBar.setProgress(0);
        orderProgressBar.setMax(10000);*/

        String title = "Order Ready";
        String body = user.getFirstName() + " your order is ready to enjoy";
        userID = user.getId();
        System.out.println("UserID: " + userID);

        System.out.println("TimerStatus Start: " + timerStatus);


        Intent intent = getIntent();
        cartID = intent.getIntExtra(PlaceOrderActivity.CART_ID, 0);
        //cartID = 252;

        if (timerStatus == 0 && cartID > 1) {

            NotificationOutput.displayNotification(this, title, body, userID, cartID);
        }

        if (cartID == 0) {
            loadData();
        }

        if (cartID == 1) {
            mOrderConfirmTextView.setText("No orders");

        } else if (cartID != 0 && cartID != 1) {
            timerStatus = 1;
            saveData(cartID, timerStatus);
        }

        System.out.println("CartID Start: " + cartID);

        User user = SharedPrefManager.getInstance(this).getUser();





        System.out.println("CartID Final: " + cartID);



            //cartID = 1;

        if (cartID > 1) {

            getOrderTime();
        }
            //System.out.println("test orderTime" + orderTimeThis);

            //mOrderConfirmTextView.setText("Thank you for your order, " + user.getFirstName() + "."
                    //+ "\nYour order will be ready in approximately " + orderTimeThis + " minutes.");








        //if (cartID == 0) {
            //mOrderConfirmTextView.setVisibility(View.INVISIBLE);
        //}


        /*
        TIMER BUTTONS
        //Start timer button listener
        mStartPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTimerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });

        //Reset button listener
        mResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });*/
    }

    private void loadData() {
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

        cartID = prefs.getInt(CART_ID, 0);
        timerStatus = prefs.getInt(TIMER_STATUS, 0);

        System.out.println("load cartID: " + cartID);
        System.out.println("load timerStatus: " + timerStatus);

    }


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
                    System.out.println("OrderTime: " + orderTime);

                    mOrderConfirmTextView.setText("Thank you for your order, " + user.getFirstName() + "."
                            + "\nYour order will be ready in approximately " + orderTime + " minutes.");

                    /* SETS TIMER
                    startTimeInMillis = orderTime * 1000 * 60;
                    System.out.println("StartTime: " + startTimeInMillis);

                    numberOfSeconds = (int) startTimeInMillis / 1000;
                    factor = 10000 / numberOfSeconds;*/
                }
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {

            }
        });
    }

    public void updateOrder() {

        updateTextView();

        int finishCartID = 1;
        int finishOrderStatus = 0;

        System.out.println("Finish cartID: " + finishCartID);
        System.out.println("Finish orderStatus: " + finishOrderStatus);

        saveData(finishCartID, finishOrderStatus);


    }

    public void updateTextView() {
        OrderConfirmationActivity.this.runOnUiThread(new Runnable() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {

                mOrderConfirmTextView.setText("Your order is ready for pick up, " + user.getFirstName() + ".");





            }
        });

    }

    /*
    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putInt("cartID", cartID);

        editor.apply();
    }


    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);

        cartID = prefs.getInt("cartID", cartID);

        getOrderTime();

        System.out.println("test" + orderTime);



    }*/

    /* TIMER METHODS
    //Starts the timer
    private void startTimer() {

        mEndTime = System.currentTimeMillis() + mTimeLeftInMillis;
        mStartPauseButton.setVisibility(View.INVISIBLE);
        mResetButton.setVisibility(View.VISIBLE);

        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();

                int secondsRemaining = (int) (millisUntilFinished / 1000);
                progressPercentage = (numberOfSeconds - secondsRemaining) * factor;
                orderProgressBar.setProgress(progressPercentage);
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                mResetButton.setVisibility(View.VISIBLE);
                //updateButtons();
                resetTimer();
                orderProgressBar.setProgress(10000);

            }
        }.start();

        mTimerRunning = true;
        //updateButtons();
    }

    public void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        //updateButtons();
    }

    private void setTimer() {
        mTimeLeftInMillis = startTimeInMillis;
        updateCountDownText();
        orderProgressBar.setProgress(0);
        mTimerRunning = false;
        mStartPauseButton.setVisibility(View.VISIBLE);
        mStartPauseButton.setText("Start");
    }

    public void resetTimer() {
        mTimeLeftInMillis = 0;
        //mTimeLeftInMillis = startTimeInMillis;
        updateCountDownText();
        orderProgressBar.setProgress(0);
    }


    public void clearTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        mTimeLeftInMillis = 0;
        updateCountDownText();
        updateTextView();
    }


    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        mCountDownTextView.setText(timeLeftFormatted);
    }


    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        //progress
        editor.putInt("progressPercentage", progressPercentage);

        editor.putLong("millisLeft", mTimeLeftInMillis);
        editor.putBoolean("timerRunning", mTimerRunning);
        editor.putLong("endTime", mEndTime);

        editor.apply();


        if (mCountDownTimer != null) mCountDownTimer.cancel();
    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);

        mTimeLeftInMillis = prefs.getLong("millisLeft", startTimeInMillis);
        mTimerRunning = prefs.getBoolean("timerRunning", false);

        updateCountDownText();
        //updateButtons();


        if (mTimerRunning) {
            mEndTime = prefs.getLong("endTime", 0);
            mTimeLeftInMillis = mEndTime - System.currentTimeMillis();
            //i = prefs.getInt("iValue", i);
            progressPercentage = prefs.getInt("progressPercentage", progressPercentage);
            orderProgressBar.setProgress(progressPercentage);

            if (mTimeLeftInMillis < 0) {
                mTimeLeftInMillis = 0;
                mTimerRunning = false;
                updateCountDownText();
                //updateButtons();

            } else startTimer();
        }
    }

    private void updateButtons() {
        if (mTimerRunning) {
            mResetButton.setVisibility(View.INVISIBLE);
            mStartPauseButton.setText("Pause");
        } else {
            mStartPauseButton.setText("Start");
            if (mTimeLeftInMillis < 1000) {
                mStartPauseButton.setVisibility(View.INVISIBLE);
            } else {
                mStartPauseButton.setVisibility(View.VISIBLE);
            }
            if (mTimeLeftInMillis < startTimeInMillis) {
                mResetButton.setVisibility(View.VISIBLE);
            } else {
                mResetButton.setVisibility(View.INVISIBLE);
            }
        }
    }*/
}
