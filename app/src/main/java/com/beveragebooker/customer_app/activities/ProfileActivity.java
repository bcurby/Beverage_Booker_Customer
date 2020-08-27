package com.beveragebooker.customer_app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.beveragebooker.customer_app.R;
import com.beveragebooker.customer_app.models.User;
import com.beveragebooker.customer_app.storage.SharedPrefManager;



public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        textView = findViewById(R.id.textViewWelcome);

        User user = SharedPrefManager.getInstance(this).getUser();

        textView.setText("Welcome back " + user.getFirstName());

        findViewById(R.id.continueButton).setOnClickListener(this);
        findViewById(R.id.signOutButton).setOnClickListener(this);

    }

    //Check to see whether the user is not logged in
    @Override
    protected void onStart() {
        super.onStart();

        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.continueButton:
                Intent intent = new Intent(ProfileActivity.this, PrimaryMenu.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;

            case R.id.signOutButton:
                SharedPrefManager.getInstance(this).clear();
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }

}
