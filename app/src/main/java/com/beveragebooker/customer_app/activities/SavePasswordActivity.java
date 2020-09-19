package com.beveragebooker.customer_app.activities;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.beveragebooker.customer_app.R;
import com.beveragebooker.customer_app.api.RetrofitClient;
import com.beveragebooker.customer_app.models.User;
import com.beveragebooker.customer_app.storage.SharedPrefManager;

public class SavePasswordActivity extends AppCompatActivity {

    EditText mPassword1, mPassword2;
    Button mSave;

    User user = SharedPrefManager.getInstance(this).getUser();
    int userID = user.getId();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_password);


        // click listener for save button
        mSave.setOnClickListener(v -> {

            saveNewPassword();

        });
    }

    private void saveNewPassword(){

        if (mPassword1.equals(mPassword2)){

            Call<ResponseBody> call = RetrofitClient
                    .getInstance()
                    .getApi()
                    .savePassword(userID, mPassword1);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call,
                                       Response<ResponseBody> response) {

                    if(response.code() == 201 ){
                        Toast.makeText(SavePasswordActivity
                                        .this, "SAVED",
                                Toast.LENGTH_LONG).show();

                        SharedPrefManager.getInstance(SavePasswordActivity.this).clear();
                        startActivity(new Intent(SavePasswordActivity.this, AccountActivity.class));

                    }else if (response.code()== 404){
                        Toast.makeText(SavePasswordActivity
                                        .this, "User not found",
                                Toast.LENGTH_LONG).show();

                    }else if (response.code() == 422){
                        Toast.makeText(SavePasswordActivity
                                        .this, "An error has occured"+ "\n" +"No " +
                                        "changes were saved",
                                Toast.LENGTH_LONG).show();
                    }

                    Log.d("WHAT IS THIS:  ", String.valueOf(response.code()));
                }


                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(SavePasswordActivity.this, t.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            });


        }


    }



}