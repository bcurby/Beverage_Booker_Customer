package com.beveragebooker.customer_app.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.ResponseBody;
import retrofit2.Call;

import com.beveragebooker.customer_app.R;
import com.beveragebooker.customer_app.api.RetrofitClient;
import com.beveragebooker.customer_app.models.User;
import com.beveragebooker.customer_app.storage.SharedPrefManager;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AccountActivity extends AppCompatActivity {

    private EditText mFirstName;
    private EditText mLastName;
    private EditText mEmail;
    private EditText mPassword;
    private EditText mPhoneNum;
    private TextView mAccountTitle;
    private Button mEditButton, mSaveButton, mDeleteButton;

    User user = SharedPrefManager.getInstance(this).getUser();
    int userID = user.getId();

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
        mDeleteButton = findViewById(R.id.deleteAccount);
        mSaveButton = findViewById(R.id.saveButton);
        mEditButton = findViewById(R.id.editButton);

        // call method for filling in the textview boxes
        setTextView();

        // click listener for delete account
        mDeleteButton.setOnClickListener(v -> {

        popupConfirmDeleteAccount();

        });
        // click listener for save button
        mSaveButton.setOnClickListener(v -> {
            saveChanges();

        });

        // click listener for edit button
        mEditButton.setOnClickListener(v -> {

            editAccount();

        });

    }

    // method for setting the edittext values which are by default disabled
    @SuppressLint("SetTextI18n")
    private void setTextView(){
        mFirstName.setText("   " + user.getFirstName());
        mLastName.setText("   " + user.getLastName());
        mEmail.setText("   " + user.getEmail());
        mPassword.setText("*************");
        mPhoneNum.setText("   " +user.getPhone());
        mAccountTitle.setText("Hi " + user.getFirstName());
    }

    // method to call api for saving changed
    private void saveChanges() {




        String firstName = String.valueOf(mFirstName.getText());
        String lastName = String.valueOf(mLastName.getText());
        String email = String.valueOf(mEmail.getText());
        String phoneNum = String.valueOf(mPhoneNum.getText());

        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getApi()
                .saveProfile(userID, firstName, lastName, email, phoneNum);
    }

    // method to call api for deleting users account
    private void deleteAccount(){
        //todo -  remove the user and open orders
        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getApi()
                .deleteUser(userID);

    }

    // method to enable editText and remove the delete button
    private void editAccount(){

        mDeleteButton.setVisibility(View.GONE);
        mDeleteButton.setEnabled(false);
        mSaveButton.setVisibility(View.VISIBLE);
        mSaveButton.setEnabled(true);
        mEditButton.setVisibility(View.GONE);
        mEditButton.setEnabled(false);


    }




    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private void popupConfirmDeleteAccount() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation");
        builder.setMessage("Are you sure you want to delete your account?");
        builder.setCancelable(false);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
    }


}


