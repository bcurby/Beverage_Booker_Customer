package com.beveragebooker.customer_app.activities;

import androidx.appcompat.app.AppCompatActivity;

import com.beveragebooker.customer_app.R;
import com.beveragebooker.customer_app.api.RetrofitClient;
import com.beveragebooker.customer_app.models.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.beveragebooker.customer_app.storage.SharedPrefManager.getInstance;

public class PlaceOrderActivity extends AppCompatActivity implements View.OnClickListener {

    int creditCardNumber, creditCardCVV, creditCardExpiryMonth, creditCardExpiryYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);

        Intent intent = getIntent();

        creditCardNumber = intent.getIntExtra(PaymentActivity.CREDIT_CARD_NUMBER, 0);
        System.out.println(creditCardNumber);

        creditCardCVV = intent.getIntExtra(PaymentActivity.CREDIT_CARD_CVV, 0);
        creditCardExpiryMonth = intent.getIntExtra(PaymentActivity.CREDIT_CARD_EXPIRY_MONTH, 0);
        creditCardExpiryYear = intent.getIntExtra(PaymentActivity.CREDIT_CARD_EXPIRY_YEAR, 0);

        //Place Order button and on click listener
        findViewById(R.id.placeOrderButton).setOnClickListener(this);
    }

    //Send the order to the server
    public void placeOrder() {

        final User loggedUser = getInstance(PlaceOrderActivity.this).getUser();
        int userID = loggedUser.getId();
        System.out.println("UserID: " + userID);

        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getApi()
                .placeOrder(userID, creditCardNumber, creditCardCVV, creditCardExpiryMonth, creditCardExpiryYear);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.code() == 303) {
                    Toast.makeText(PlaceOrderActivity.this, "Your order was placed successfully", Toast.LENGTH_LONG).show();

                } else if (response.code() == 304) {
                    Toast.makeText(PlaceOrderActivity.this, "There was a problem placing your order",
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Toast.makeText(PlaceOrderActivity.this, t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public void onClick(View v) {
        placeOrder();
        startActivity(new Intent(this, OrderConfirmationActivity.class));
    }
}
