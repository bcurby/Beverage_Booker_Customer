package com.beveragebooker.customer_app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.beveragebooker.customer_app.R;

public class SelectOrderTypeActivity extends AppCompatActivity {

    public static String CART_TOTAL_ORDER_TYPE = "com.beveragebooker.customer_app.CART_TOTAL_ORDER_TYPE";

    private Button PickUpButton;
    private Button DeliveryButton;

    private String orderTotal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_order_type);

        Intent intent = getIntent();
        orderTotal = intent.getStringExtra(CartActivity.CART_TOTAL);

        //PickUp Button
        PickUpButton = findViewById(R.id.PickUpButton);
        PickUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPayment();
            }
        });

        //Delivery Button
        DeliveryButton = findViewById(R.id.DeliveryButton);
        DeliveryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToBookDelivery();
            }
        });

    }

    private void goToPayment() {
        System.out.println("Order Test2: " + orderTotal);
        Intent intent = new Intent(this, PlaceOrderActivity.class );
        intent.putExtra(CART_TOTAL_ORDER_TYPE, orderTotal);
        startActivity(intent);
    }

    private void goToBookDelivery() {
        System.out.println("Order Test2: " + orderTotal);
        Intent intent = new Intent(this, BookDeliveryActivity.class );
        intent.putExtra(CART_TOTAL_ORDER_TYPE, orderTotal);
        startActivity(intent);
    }

}
