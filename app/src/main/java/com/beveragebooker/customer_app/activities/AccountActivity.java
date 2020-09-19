package com.beveragebooker.customer_app.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.beveragebooker.customer_app.R;
import com.beveragebooker.customer_app.api.RetrofitClient;
import com.beveragebooker.customer_app.models.User;
import com.beveragebooker.customer_app.storage.SharedPrefManager;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AccountActivity extends AppCompatActivity {

    private EditText mFirstName;
    private EditText mLastName;
    private EditText mEmail;
    private EditText mPhoneNum;
    private TextView mAccountTitle;
    private Button mEditButton, mSaveButton, mDeleteButton, mUserPassword;

    User user = SharedPrefManager.getInstance(this).getUser();
    int userID = user.getId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        mFirstName = findViewById(R.id.userFirstName);
        mLastName = findViewById(R.id.userLastName);
        mEmail = findViewById(R.id.userEmail);
        mPhoneNum = findViewById(R.id.userPhoneNum);
        mAccountTitle = findViewById(R.id.accountTitle);
        mDeleteButton = findViewById(R.id.deleteAccount);
        mSaveButton = findViewById(R.id.saveButton);
        mEditButton = findViewById(R.id.editButton);
        mUserPassword = findViewById(R.id.userPassword);

        // call method for filling in the textview boxes
        setTextView();

        // click listener for delete account
        mDeleteButton.setOnClickListener(v -> {
            deleteAccount();

        });
        // click listener for save button
        mSaveButton.setOnClickListener(v -> {
            saveChanges();

        });

        // click listener for edit button
        mEditButton.setOnClickListener(v -> {
            editAccount();

        });

        mUserPassword.setOnClickListener(v -> {
            Intent intent = new Intent(AccountActivity.this, SavePasswordActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

    }

    // method for setting the edittext values which are by default disabled
    @SuppressLint("SetTextI18n")
    private void setTextView(){
        mFirstName.setText("   " + user.getFirstName());
        mLastName.setText("   " + user.getLastName());
        mEmail.setText("   " + user.getEmail());
        mPhoneNum.setText("   " +user.getPhone());
        mAccountTitle.setText("Hi " + user.getFirstName());
    }

    // method to call api for saving changed
    private void saveChanges() {

        String email = mEmail.getText().toString().trim();
        String firstName = mFirstName.getText().toString().trim();
        String lastName = mLastName.getText().toString().trim();
        String phoneNum = mPhoneNum.getText().toString().trim();


        if (email.isEmpty()) {
            mEmail.setError("Email is required");
            mEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmail.setError("Enter a valid email");
            mEmail.requestFocus();
            return;
        }

        if (firstName.isEmpty()) {
            mFirstName.setError("First Name required");
            mFirstName.requestFocus();
            return;
        }

        if (lastName.isEmpty()) {
            mLastName.setError("Last Name required");
            mLastName.requestFocus();
            return;
        }

        if (phoneNum.isEmpty()) {
            mPhoneNum.setError("Mobile Number required");
            mPhoneNum.requestFocus();
            return;
        }

        if (phoneNum.length() != 10) {
            mPhoneNum.setError("Mobile Number must be 10 digits");
            mPhoneNum.requestFocus();
            return;
        }

        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getApi()
                .saveProfile(userID, firstName, lastName, email, phoneNum);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call,
                                   Response<ResponseBody> response) {

                if(response.code() == 201 ){
                    Toast.makeText(AccountActivity
                                    .this, "SAVED",
                            Toast.LENGTH_LONG).show();

                }else if (response.code()== 404){
                        Toast.makeText(AccountActivity
                                        .this, "Account not found",
                                Toast.LENGTH_LONG).show();

                }else if (response.code() == 422){
                        Toast.makeText(AccountActivity
                                        .this, "An error has occured"+ "\n" +"No " +
                                        "changes were saved",
                                Toast.LENGTH_LONG).show();
                }

                Log.d("WHAT IS THIS:  ", String.valueOf(response.code()));
            }


            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(AccountActivity.this, t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });

        //change buttons visibility and usability
        mDeleteButton.setVisibility(View.VISIBLE);
        mDeleteButton.setEnabled(true);
        mSaveButton.setVisibility(View.GONE);
        mSaveButton.setEnabled(false);
        mEditButton.setVisibility(View.VISIBLE);
        mEditButton.setEnabled(true);

        //set edit text fields as locked
        mFirstName.setEnabled(false);
        mLastName.setEnabled(false);
        mEmail.setEnabled(false);
        mPhoneNum.setEnabled(false);
        mAccountTitle.setEnabled(false);

    }

    // method to call api for deleting users account
    private void deleteAccount(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation");
        builder.setMessage("Are you sure you want to delete your account?");
        builder.setCancelable(false);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Call<ResponseBody> call = RetrofitClient
                                        .getInstance()
                                        .getApi()
                                        .deleteUser(userID);

                        call.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call,
                                                   Response<ResponseBody> response) {

                                if(response.code() == 201){

                                    Toast.makeText(AccountActivity
                                                    .this, "Account has been deleted",
                                            Toast.LENGTH_LONG).show();
                                    SharedPrefManager.getInstance(AccountActivity.this).clear();
                                    startActivity(new Intent(AccountActivity.this, MainActivity.class));

                                } else if (response.code() == 402 || response.code() == 422) {

                                    Toast.makeText(AccountActivity
                                                    .this, "Account can not be " +
                                                    "deleted an error occurred",
                                            Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {

                            }
                        });

                    }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> {
        });
        builder.show();
    }

    // method to enable editText and remove the delete button
    private void editAccount(){

        //change buttons visibility and usability
        mDeleteButton.setVisibility(View.GONE);
        mDeleteButton.setEnabled(false);
        mSaveButton.setVisibility(View.VISIBLE);
        mSaveButton.setEnabled(true);
        mEditButton.setVisibility(View.GONE);
        mEditButton.setEnabled(false);

        //set edit text fields as editable
        mFirstName.setEnabled(true);
        mLastName.setEnabled(true);
        mEmail.setEnabled(true);
        mPhoneNum.setEnabled(true);
        mAccountTitle.setEnabled(true);
    }
}


