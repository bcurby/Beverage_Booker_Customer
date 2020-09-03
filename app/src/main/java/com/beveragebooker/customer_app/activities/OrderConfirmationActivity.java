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
import com.beveragebooker.customer_app.models.User;
import com.beveragebooker.customer_app.notifications.NotificationOutput;
import com.beveragebooker.customer_app.storage.SharedPrefManager;

import java.util.Locale;

public class OrderConfirmationActivity extends AppCompatActivity {
    private static long START_TIME_IN_MILLIS = 20000;

    private TextView mOrderConfirmTextView;
    private TextView mOrderCompleted;
    private TextView mCountDownTextView;
    private Button mReturnToMainMenuButton;
    private Button mStartPauseButton;
    private Button mResetButton;

    private int cartID;
    ProgressBar orderProgressBar;
    int percentage = 5;
    int i;
    int progressPercentage;
    int numberOfSeconds = (int)START_TIME_IN_MILLIS/1000;
    int factor = 100/numberOfSeconds;


    //private NotificationOutput notifOut;

    private CountDownTimer mCountDownTimer;

    private boolean mTimerRunning;

    private long mTimeLeftInMillis;
    private long mEndTime;
    User user = SharedPrefManager.getInstance(this).getUser();

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
        orderProgressBar.setMax(100);


        Intent intent = getIntent();
        cartID = intent.getIntExtra(PlaceOrderActivity.CART_ID, 0);
        System.out.println("CartID Confirm: " + cartID);

        User user = SharedPrefManager.getInstance(this).getUser();

        mOrderConfirmTextView.setText("Thank you for your order, " + user.getFirstName() + "."
                + "\nYour order will be ready shortly.");

        mOrderCompleted = (TextView) findViewById(R.id.textViewOrderComplete);
        //mOrderCompleted.setText("Your order is ready for pick up,   " + user.getFirstName() + ".");


        String title = "Order Ready";
        String body = user.getFirstName() + " your order is ready to enjoy";
        int userID = user.getId();
        System.out.println("UserID: " + userID);

        mStartPauseButton.setVisibility(View.VISIBLE);
        mStartPauseButton.setText("Pause");

        if (cartID != 0) {
            //add the call for the completed order notification
            NotificationOutput.displayNotification(this, title, body, userID, cartID);

            //SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
            //mTimerRunning = prefs.getBoolean("timerRunning", false);

            //if (mTimerRunning == false) {
            resetTimer();
            startTimer();
        }

        //So nothing displays if user checks finished order
        //use orderStatus??
        if (cartID == 0) {
            mOrderConfirmTextView.setVisibility(View.INVISIBLE);
        }




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
                mStartPauseButton.setText("Pause");
                updateCountDownText();
                //i++;



                int secondsRemaining = (int) (millisUntilFinished / 1000);
                progressPercentage = (numberOfSeconds-secondsRemaining) * factor ;
                orderProgressBar.setProgress(progressPercentage);
                //System.out.println("Progress: " + progressPercentage);
                //orderProgressBar.setProgress((int)i*100/(60000/1000));
                //orderProgressBar.setProgress(i*percentage);
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                mStartPauseButton.setText("Start");
                mStartPauseButton.setVisibility(View.INVISIBLE);
                mResetButton.setVisibility(View.VISIBLE);
                updateTextView();
                orderProgressBar.setProgress(100);

            }
        }.start();

        mTimerRunning = true;
    }

    public void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        mStartPauseButton.setText("Start");
        mResetButton.setVisibility(View.VISIBLE);
    }

    public void resetTimer() {

        //mCountDownTimer.cancel();
        //mTimerRunning = false;
        //mTimeLeftInMillis = 0;
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
        mResetButton.setVisibility(View.INVISIBLE);
        mStartPauseButton.setVisibility(View.VISIBLE);
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


        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);

        mTimeLeftInMillis = prefs.getLong("millisLeft", START_TIME_IN_MILLIS);
        mTimerRunning = prefs.getBoolean("timerRunning", false);


        updateCountDownText();


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
            } else {
                startTimer();
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
