package com.beveragebooker.customer_app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.beveragebooker.customer_app.R;
import com.beveragebooker.customer_app.models.User;
import com.beveragebooker.customer_app.notifications.NotificationOutput;
import com.beveragebooker.customer_app.storage.SharedPrefManager;

import java.util.Locale;

public class OrderConfirmationActivity extends AppCompatActivity {
    private static long START_TIME_IN_MILLIS = 600000;

    private TextView mOrderConfirmTextView;
    private TextView mCountDownTextView;
    private Button mReturnToMainMenuButton;
    private Button mStartPauseButton;
    private Button mResetButton;

    private NotificationOutput notifOut;

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

        User user = SharedPrefManager.getInstance(this).getUser();

        mOrderConfirmTextView.setText("Thank you for your order, " + user.getFirstName() + "."
                + "\nYour order will be ready shortly.");
        String title = "Order Ready";
        String body = user.getFirstName() + " your order is ready to enjoy";
        int userID = user.getId();
        System.out.println("UserID: " + userID);
        //add the call for the completed order notification
        //NotificationOutput.displayNotification(this, title, body, userID);
        NotificationOutput.displayNotification(this, title, body, userID);
        //notifOut = new NotificationOutput(this);

        //notifOut.setListener(new NotificationOutput().Listener)
        //no.stopTimer(this);

        startTimer();
        mStartPauseButton.setVisibility(View.VISIBLE);
        mStartPauseButton.setText("Pause");

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

        //updateCountDownText();
    }

    private void startTimer() {
        mEndTime = System.currentTimeMillis() + mTimeLeftInMillis;

        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                mStartPauseButton.setText("Pause");
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                mStartPauseButton.setText("Start");
                mStartPauseButton.setVisibility(View.INVISIBLE);
                mResetButton.setVisibility(View.VISIBLE);
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
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
        mResetButton.setVisibility(View.INVISIBLE);
        mStartPauseButton.setVisibility(View.VISIBLE);
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

            if (mTimeLeftInMillis < 0) {
                mTimeLeftInMillis = 0;
                mTimerRunning = false;
                updateCountDownText();
            } else {
                startTimer();
            }
        }
    }

    public void setMillis() {
        mTimeLeftInMillis = 0;
        mTimerRunning = false;
        updateCountDownText();
    }

    //private static void setMillis() {
     //   START_TIME_IN_MILLIS = 0;
    //}

    private void openPrimaryMenu() {
        Intent intent = new Intent(this, PrimaryMenu.class);
        startActivity(intent);
    }
}
