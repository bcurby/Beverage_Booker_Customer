package com.beveragebooker.customer_app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.beveragebooker.customer_app.R;
import com.beveragebooker.customer_app.api.RetrofitClient;
import com.beveragebooker.customer_app.models.User;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import static com.beveragebooker.customer_app.storage.SharedPrefManager.*;


public class PlaceOrderActivity extends AppCompatActivity implements View.OnClickListener {

    private int creditCardNumber, creditCardCVV, expiryMonth, expiryYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);

        Intent intent = getIntent();
        creditCardNumber = intent.getIntExtra(PaymentActivity.CREDIT_CARD_NUMBER, 0);
        creditCardCVV = intent.getIntExtra(PaymentActivity.CREDIT_CARD_CVV, 0);
        expiryMonth = intent.getIntExtra(PaymentActivity.CREDIT_CARD_EXPIRY_MONTH, 0);
        expiryYear = intent.getIntExtra(PaymentActivity.CREDIT_CARD_EXPIRY_YEAR, 0);

        findViewById(R.id.placeOrderButton).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        placeOrder();
    }

    private void placeOrder() {
        final User loggedUser = getInstance(PlaceOrderActivity.this).getUser();
        int userID = loggedUser.getId();

        //int creditCardNumber = 30;
        //int creditCardCVV = 30;
        //int creditCardExpiryMonth = 30;
        //int expiryYear = 30;

        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getApi()
                .placeOrder(userID, creditCardNumber, creditCardCVV, expiryMonth, expiryYear);

        System.out.println(creditCardNumber);
        System.out.println(creditCardCVV);
        System.out.println(expiryMonth);
        System.out.println(expiryYear);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.code() == 401) {
                    Toast.makeText(PlaceOrderActivity.this, "Your order has been placed",
                            Toast.LENGTH_LONG).show();

                } else if (response.code() == 402) {
                    Toast.makeText(PlaceOrderActivity.this, "There was a problem placing your order",
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(PlaceOrderActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}

