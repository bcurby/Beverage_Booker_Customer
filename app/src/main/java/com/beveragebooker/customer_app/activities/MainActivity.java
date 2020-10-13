package com.beveragebooker.customer_app.activities;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.beveragebooker.customer_app.models.LoginResponse;
import com.beveragebooker.customer_app.R;
import com.beveragebooker.customer_app.api.RetrofitClient;
import com.beveragebooker.customer_app.models.LoginResponse;
import com.beveragebooker.customer_app.notifications.NotificationOutput;
import com.beveragebooker.customer_app.storage.SharedPrefManager;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String CHANNEL_ID = "beverageBooker";
    private static final String CHANNEL_NAME = "orderReady";
    private static final String CHANNEL_DESC = "notify customer order is ready";
    private EditText editTextEmail;
    private EditText editTextPassword;
    private static final String TAG = "MainActivity";

    private TextView needHelpLogin;

    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription(CHANNEL_DESC);
            NotificationManager manager = getSystemService(NotificationManager.class);
            assert manager != null;
            manager.createNotificationChannel(channel);

            setContentView(R.layout.activity_main);

            editTextEmail = findViewById(R.id.editTextEmailLogin);
            editTextPassword = findViewById(R.id.editTextPasswordLogin);
            needHelpLogin = findViewById(R.id.textViewNeedHelp);

            findViewById(R.id.buttonLogin).setOnClickListener(this);
            findViewById(R.id.textViewRegister).setOnClickListener(this);

            needHelpLogin.setOnClickListener(v -> {
                Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtu.be/DI7c75-eGwQ?t=63"));
                Intent webIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://youtu.be/DI7c75-eGwQ?t=63"));
                try {
                    MainActivity.this.startActivity(appIntent);
                } catch (ActivityNotFoundException ex) {
                    MainActivity.this.startActivity(webIntent);
                }
            });
        }

    }

    //Check to see whether user is already logged in
    @Override
    protected void onStart() {
        super.onStart();

        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            Intent intent = new Intent(this, ProfileActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    //Logs user into existing account
    private void userLogin() {

        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (email.isEmpty()) {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please enter a valid email");
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("Password required");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editTextPassword.setError("Password should be at least 6 characters long");
            editTextPassword.requestFocus();
            return;
        }

        Call<LoginResponse> call = RetrofitClient
                .getInstance().getApi().userLogin(email, password);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();

                if (response.code() == 202) {

                    //Proceed with Login
                    assert loginResponse != null;
                    SharedPrefManager.getInstance(MainActivity.this)
                            .saveUser(loginResponse.getUser());

                    Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                } else if (response.code() == 200) {
                    Toast.makeText(MainActivity.this, "User does not exist",
                            Toast.LENGTH_LONG).show();

                } else if (response.code() == 401) {
                    Toast.makeText(MainActivity.this, "Invalid login credentials",
                            Toast.LENGTH_LONG).show();
                }
            }


            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.buttonLogin:
                userLogin();

                break;

            case R.id.textViewRegister:
                startActivity(new Intent(this, CreateUserActivity.class));
                break;


        }
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please press BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}
