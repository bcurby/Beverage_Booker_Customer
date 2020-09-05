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

    private long startTimeInMillis;

    private TextView mOrderConfirmTextView;
    private TextView mOrderCompleted;
    private TextView mCountDownTextView;
    private Button mReturnToMainMenuButton;
    private Button mStartPauseButton;
    private Button mResetButton;

    int cartID;
    int userID;
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
        mReturnToMainMenuButton = findViewById(R.id.returnToMainMenu);
        mStartPauseButton = findViewById(R.id.button_start_pause);
        mResetButton = findViewById(R.id.button_reset);

        orderProgressBar = findViewById(R.id.my_progress_bar);

        orderProgressBar.setProgress(0);
        orderProgressBar.setMax(10000);


        Intent intent = getIntent();
        cartID = intent.getIntExtra(PlaceOrderActivity.CART_ID, 0);
        cartID = 230;
        System.out.println("CartID Start: " + cartID);

        User user = SharedPrefManager.getInstance(this).getUser();

        mOrderConfirmTextView.setText("Thank you for your order, " + user.getFirstName() + "."
                + "\nYour order will be ready shortly.");

        mOrderCompleted = (TextView) findViewById(R.id.textViewOrderComplete);
        //mOrderCompleted.setText("Your order is ready for pick up,   " + user.getFirstName() + ".");


        String title = "Order Ready";
        String body = user.getFirstName() + " your order is ready to enjoy";
        userID = user.getId();
        System.out.println("UserID: " + userID);

        //mStartPauseButton.setVisibility(View.VISIBLE);
        //mStartPauseButton.setText("Pause");

        //cartID = getCartID(userID);


        //getCartID();

        //setCartID();



        //cartID = thisCart.getCartID();
        //System.out.println("CartID Final: " + cartID);

        if (cartID != 0) {
            //add the call for the completed order notification
            //System.out.println("CartID Passed: " + cartID);
            NotificationOutput.displayNotification(this, title, body, userID, cartID);

            //SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
            //mTimerRunning = prefs.getBoolean("timerRunning", false);
            getOrderTime();
            //System.out.println("Running Status: " + mTimerRunning);

            //if (mTimerRunning == true) {
            //pauseTimer();
            //resetTimer();
            //clearTimer();
            //resetTimer();
            //setTimer();
            //startTimer();


        }
          // onStart();
        //}
        if (cartID == 0) {
            mOrderConfirmTextView.setVisibility(View.INVISIBLE);
        }

        //So nothing displays if user checks finished order
        //use orderStatus??

        //if (orderStatus == 0) {
            //mOrderConfirmTextView.setVisibility(View.INVISIBLE);
           // updateCountDownText();
       // }


        mReturnToMainMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPrimaryMenu();
            }
        });

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

        mResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });
    }

    private void getOrderTime() {

        Call<Order> call = RetrofitClient
                .getInstance()
                .getApi()
                .getOrderStatus(userID, cartID);

        call.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                if (response.code() == 200) {
                    Order thisOrder = response.body();
                    int orderTime = thisOrder.getOrderTime();
                    System.out.println("OrderTime: " + orderTime);
                    startTimeInMillis = orderTime * 1000 * 60;
                    System.out.println("StartTime: " + startTimeInMillis);

                    numberOfSeconds = (int) startTimeInMillis / 1000;
                    factor = 10000 / numberOfSeconds;

                    //resetTimer();
                    //startTimer();

                }
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {

            }
        });
    }



    /*@Override
    protected void onResume() {
        super.onResume();
        if (cartID != 0) {
            System.out.println("onResume: " + cartID);
            startTimer();
        }
    }*/

    //private void setCartID() {

       // SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        //cartID = prefs.getInt("cartID", newcartID);
        //System.out.println("CartID Set: " + newcartID);
    //}

    /*private void checkOrderStatus() {

        System.out.println("Updated UserID: " + userID);

        myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {

                Call<Order> call = RetrofitClient
                        .getInstance()
                        .getApi()
                        .getCartIDFromUsers(userID);

                call.enqueue(new Callback<Order>() {
                    @Override
                    public void onResponse(Call<Order> call, Response<Order> response) {
                        if (response.code() == 200) {

                            Order thisOrder = response.body();
                            int thiscartID = thisOrder.getCartID();
                            int thisOrderStatus = thisOrder.getOrderStatus();
                            System.out.println("Updated CartID: " + thiscartID);
                            System.out.println("Updated OrderStatus: " + thisOrderStatus);

                            if (thisOrderStatus == 1 && thiscartID != 0) {
                                mOrderConfirmTextView.setText("Your order is still in progress");
                            } else if (thisOrderStatus == 0 && thiscartID != 0) {
                                mOrderConfirmTextView.setText("Your order is ready");
                                myTimer.cancel();
                            }
                            //SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
                            //SharedPreferences.Editor editor = prefs.edit();

                            //progress
                            //editor.putInt("cartID", cartID);
                        }
                    }


                    @Override
                    public void onFailure(Call<Order> call, Throwable t) {

                    }
                });
            }
        }, 0, 10000);
    }

    /*private void getCartID() {

        System.out.println("Updated UserID: " + userID);

        Call<Cart> call = RetrofitClient
                .getInstance()
                .getApi()
                .getCartIDFromUsers(userID);

        call.enqueue(new Callback<Cart>() {
            @Override
            public void onResponse(Call<Cart> call, Response<Cart> response) {
                if (response.code() == 200) {

                    thisCart = response.body();
                    int returncartID = thisCart.getCartID();
                    cartID = returncartID;
                    System.out.println("Updated CartID: " + cartID);

                    SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();

                    //progress
                    editor.putInt("cartID", cartID);
                }
            }


            @Override
            public void onFailure(Call<Cart> call, Throwable t) {

            }
        });

    //return cartID;
    }*/

    private void startTimer() {

        //int numberOfSeconds = 600;
        //int factor = 100/numberOfSeconds;
        //System.out.println("Factor: " + factor);

        //mTimeLeftInMillis = START_TIME_IN_MILLIS;
        mEndTime = System.currentTimeMillis() + mTimeLeftInMillis;


        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();



                int secondsRemaining = (int) (millisUntilFinished / 1000);
                progressPercentage = (numberOfSeconds - secondsRemaining) * factor;
                orderProgressBar.setProgress(progressPercentage);
                //System.out.println("Progress: " + progressPercentage);
                //orderProgressBar.setProgress((int)i*100/(60000/1000));
                //orderProgressBar.setProgress(i*percentage);
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                //mStartPauseButton.setText("Start");
                //mStartPauseButton.setVisibility(View.INVISIBLE);
                //mResetButton.setVisibility(View.VISIBLE);
                updateButtons();
                resetTimer();
                orderProgressBar.setProgress(10000);

                //getCartID(userID);
                //checkOrderStatus(cartID);

                //checkOrderStatus();

                //if (orderStatus == 0) {
                  //  updateTextView();
                   // System.out.println("orderStatus Timer Finish: " + orderStatus);
               // } else if (orderStatus == 1) {
                  //  mOrderConfirmTextView.setText("Your order is close to ready, " + user.getFirstName() + ".");
               // }

            }
        }.start();

        mTimerRunning = true;
        updateButtons();
    }

    public void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        //mStartPauseButton.setText("Start");
        //mResetButton.setVisibility(View.VISIBLE);
        updateButtons();
    }

    private void setTimer() {
        mTimeLeftInMillis = startTimeInMillis;
        updateCountDownText();
        orderProgressBar.setProgress(0);
        mTimerRunning = false;
    }

    public void resetTimer() {

        //mCountDownTimer.cancel();
        //mTimerRunning = false;
        //mTimeLeftInMillis = 0;
        mTimeLeftInMillis = startTimeInMillis;
        updateCountDownText();
        updateButtons();
        //mResetButton.setVisibility(View.INVISIBLE);
        //mStartPauseButton.setVisibility(View.VISIBLE);
        orderProgressBar.setProgress(0);
    }


    public void clearTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        mTimeLeftInMillis = 0;
        updateCountDownText();
        updateTextView();
    }

    private void updateTextView() {
        OrderConfirmationActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mOrderConfirmTextView.setText("Your order is ready for pick up, " + user.getFirstName() + ".");
                //mOrderCompleted.setVisibility(View.VISIBLE);
            }
        });

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
        //editor.putInt("iValue", i);

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
        mTimerRunning = prefs.getBoolean("timerRunning", true);
        //System.out.println("Timer: " + mTimerRunning);

        //progressPercentage = prefs.getInt("progressPercentage", progressPercentage);


        updateCountDownText();
        updateButtons();
        //checkOrderStatus();

        //getCartID(userID);
        //checkOrderStatus(cartID);


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
                updateButtons();
                //updateTextView();
                //orderProgressBar.setProgress(progressPercentage);
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
    }

    //public void setMillis() {
    //  mTimeLeftInMillis = 0;
    //  mTimerRunning = false;
    //  updateCountDownText();
    // }


    private void openPrimaryMenu() {
        Intent intent = new Intent(this, PrimaryMenu.class);
        startActivity(intent);
    }
}
