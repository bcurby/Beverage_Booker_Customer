package com.beveragebooker.customer_app.activities;

import androidx.appcompat.app.AppCompatActivity;

import com.beveragebooker.customer_app.R;
import com.beveragebooker.customer_app.models.User;
import com.beveragebooker.customer_app.storage.SharedPrefManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AccountActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mFirstName;
    private TextView mLastName;
    private TextView mEmail;
    private TextView mPassword;
    private TextView mPhoneNum;
    private TextView mAccountTitle;

    User user = SharedPrefManager.getInstance(this).getUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        mFirstName = findViewById(R.id.userFirstName);
        mLastName = findViewById(R.id.userLastName);
        mEmail = findViewById(R.id.userEmail);
        mPassword = findViewById(R.id.userPassword);
        mPhoneNum = findViewById(R.id.userPhoneNum);
        mAccountTitle = findViewById(R.id.accountTitle);

        Log.d("WHAT IS THIS", "this" + user.getLastName());


       findViewById(R.id.editButton).setOnClickListener(this);
       findViewById(R.id.saveButton).setOnClickListener(this);

        //call method for filling in the textview boxes
        setTextView();






    }

    @SuppressLint("SetTextI18n")
    private void setTextView(){
        mFirstName.setText(user.getFirstName());
        mLastName.setText(user.getLastName());
        mEmail.setText(user.getEmail());
        mPassword.setText("********");
        mPhoneNum.setText(user.getPhone());
        mAccountTitle.setText("Hi " + user.getFirstName());





    }

    private void saveChanges() {


    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.editButton:

                break;

            case R.id.saveButton:

                System.out.println("Just to fill it in");
                break;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}


