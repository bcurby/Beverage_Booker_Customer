package com.beveragebooker.customer_app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.beveragebooker.customer_app.R;
import com.beveragebooker.customer_app.adapters.CartAdapter;
import com.beveragebooker.customer_app.api.RetrofitClient;
import com.beveragebooker.customer_app.models.MenuItem;
import com.beveragebooker.customer_app.models.User;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import static com.beveragebooker.customer_app.storage.SharedPrefManager.*;


public class PlaceOrderActivity extends AppCompatActivity implements View.OnClickListener {

    private int creditCardCVV, expiryMonth, expiryYear;
    private long creditCardNumber;

    private String streetNumber, streetName, cityTown;
    private int postCode, deliveryStatusInt;
    private boolean deliveryStatus;

    private RecyclerView mRecyclerView;
    private CartAdapter mCartAdapter;

    private List<MenuItem> cartItemList;

    private TextView orderTotal;

    DecimalFormat currency = new DecimalFormat("###0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);

        Intent intent = getIntent();
        creditCardNumber = intent.getLongExtra(PaymentActivity.CREDIT_CARD_NUMBER, 0);
        creditCardCVV = intent.getIntExtra(PaymentActivity.CREDIT_CARD_CVV, 0);
        expiryMonth = intent.getIntExtra(PaymentActivity.CREDIT_CARD_EXPIRY_MONTH, 0);
        expiryYear = intent.getIntExtra(PaymentActivity.CREDIT_CARD_EXPIRY_YEAR, 0);

        streetNumber = intent.getStringExtra(PaymentActivity.STREET_NUMBER);
        streetName = intent.getStringExtra(PaymentActivity.STREET_NAME);
        postCode = intent.getIntExtra(PaymentActivity.POST_CODE, 0);
        cityTown = intent.getStringExtra(PaymentActivity.CITY_TOWN);
        deliveryStatus = intent.getBooleanExtra(PaymentActivity.DELIVERY_STATUS, false);
        if(deliveryStatus == true) {
            deliveryStatusInt = 1;
        } else {
            deliveryStatusInt = 0;
        }
        findViewById(R.id.placeOrderButton).setOnClickListener(this);

        //Recyclerview
        cartItemList = new ArrayList<>();

        mRecyclerView = findViewById(R.id.placeOrderRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mCartAdapter = new CartAdapter(cartItemList);

        mRecyclerView.setAdapter(mCartAdapter);

        orderTotal = findViewById(R.id.orderTotal);

        User loggedUser = getInstance(PlaceOrderActivity.this).getUser();
        int userID = loggedUser.getId();

        Call<List<MenuItem>> call = RetrofitClient
                .getInstance()
                .getApi()
                .getCartItems(userID);

        call.enqueue(new Callback<List<MenuItem>>() {
            @Override
            public void onResponse(Call<List<MenuItem>> call, Response<List<MenuItem>> response) {

                //Cart items are retrieved from the database
                if (response.code() == 201) {
                    for (int i = 0; i < response.body().size(); i++) {
                        cartItemList.add(response.body().get(i));
                    }
                    mCartAdapter.notifyDataSetChanged();
                }
                //Display the total of the items in the order
                orderTotal.setText("Order Total: $" + currency.format(getOrderTotal()));
            }

            @Override
            public void onFailure(Call<List<MenuItem>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if(deliveryStatus == true) {
            createNewDelivery();
        }
        placeOrder();
    }

    private void placeOrder() {

        final User loggedUser = getInstance(PlaceOrderActivity.this).getUser();
        int userID = loggedUser.getId();

        double orderTotal = getOrderTotal();

        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getApi()
                .placeOrder(userID, creditCardNumber, creditCardCVV, expiryMonth, expiryYear, deliveryStatusInt, orderTotal);

        System.out.println(userID);
        System.out.println(creditCardNumber);
        System.out.println(creditCardCVV);
        System.out.println(expiryMonth);
        System.out.println(expiryYear);
        System.out.println(deliveryStatus);
        System.out.println(orderTotal);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.code() == 201) {

                    Intent intent = new Intent(PlaceOrderActivity.this, OrderConfirmationActivity.class);

                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);


                } else if (response.code() == 422) {
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

    private void createNewDelivery() {
        User loggedUser = getInstance(PlaceOrderActivity.this).getUser();
        int userID = loggedUser.getId();

        System.out.println("userID: " + userID + " " + streetNumber + " " + streetName + " " + postCode + " " + cityTown);

        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getApi()
                .bookDelivery(userID, streetNumber, streetName, postCode, cityTown);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 201) {
                    Toast.makeText(PlaceOrderActivity.this, "Delivery Submitted", Toast.LENGTH_LONG).show();
                }else if (response.code() == 422) {
                    Toast.makeText(PlaceOrderActivity.this, "Delivery Failed", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(PlaceOrderActivity.this, t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private double getOrderTotal() {

        double orderTotal = 0;

        for (int i = 0; i < cartItemList.size(); i++) {

            int quantity = cartItemList.get(i).getQuantity();
            System.out.println("Quantity: " + quantity);

            double price = cartItemList.get(i).getPrice();
            System.out.println("Price: " + price);

            double itemTotal = quantity * price;
            System.out.println("Item Total: " + itemTotal);

            orderTotal += itemTotal;
            System.out.println("Cart Total: " + orderTotal);


        }
        return orderTotal;

    }
}

