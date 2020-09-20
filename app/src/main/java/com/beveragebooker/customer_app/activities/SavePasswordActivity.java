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

        mSave = findViewById(R.id.save);
        mPassword1 = findViewById(R.id.password1);
        mPassword2 = findViewById(R.id.password2);
Log.d("USER ID", String.valueOf(userID));
        // click listener for save button
        mSave.setOnClickListener(v -> {

            saveNewPassword();
        });
    }

    private void saveNewPassword(){

        String p1 = String.valueOf(mPassword1.getText());
        String p2 = String.valueOf(mPassword2.getText());

        if (p1.equals(p2)){

            Call<ResponseBody> call = RetrofitClient
                    .getInstance()
                    .getApi()
                    .savePassword(userID, p1);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call,
                                       Response<ResponseBody> response) {

                    if(response.code() == 201 ){
                        Toast.makeText(SavePasswordActivity
                                        .this, "SAVED",
                                Toast.LENGTH_LONG).show();

                        startActivity(new Intent(SavePasswordActivity.this,
                                AccountActivity.class));

                    }else if (response.code()== 402){
                        Toast.makeText(SavePasswordActivity
                                        .this, "User not found",
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
        }else {

            mPassword1.setError("Passwords do not match");
            mPassword1.requestFocus();
        }
    }
}